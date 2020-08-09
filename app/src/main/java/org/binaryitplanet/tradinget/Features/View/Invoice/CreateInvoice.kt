package org.binaryitplanet.tradinget.Features.View.Invoice

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_create_invoice.view.*
import org.binaryitplanet.tradinget.Features.Adapter.GoodsAdapter
import org.binaryitplanet.tradinget.Features.Adapter.NoteListAdapter
import org.binaryitplanet.tradinget.Features.Prsenter.LedgerPresenterIml
import org.binaryitplanet.tradinget.Features.View.Ledger.ViewLedgers
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.GoodUtils
import org.binaryitplanet.tradinget.Utils.InvoiceUtils
import org.binaryitplanet.tradinget.databinding.ActivityCreateInvoiceBinding
import java.util.*
import kotlin.collections.ArrayList

class CreateInvoice : AppCompatActivity(), ViewLedgers {
    private val TAG = "CreateInvoice"
    private lateinit var binding: ActivityCreateInvoiceBinding

    // Variables
    private lateinit var invoiceName: String

    private var goodsList = arrayListOf<GoodUtils>()
    private var notesList = arrayListOf<String>()
    private var ledgerList = arrayListOf<String>()
    
    private var ecommerceDay: Int = 0
    private var ecommerceMonth: Int = 0
    private var ecommerceYear: Int = 0
    
    private var shippingDay: Int = 0
    private var shippingMonth: Int = 0
    private var shippingYear: Int = 0
    
    private var invoiceDay: Int = 0
    private var invoiceMonth: Int = 0
    private var invoiceYear: Int = 0
    
    private lateinit var ecommerceDateString: String
    private lateinit var invoiceDateString: String
    private lateinit var shippingDateString: String
    
    private lateinit var goodsName: String
    private lateinit var goodsGstRateString: String
    private lateinit var goodsMou: String
    private lateinit var goodsQuantityString: String
    private lateinit var goodsRateString: String
    private lateinit var goodsTotalString: String

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_invoice)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_invoice)

        setUpToolbar()

        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.done && checkValidity()) {

                val permissions:Array<String> = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permissions, Config.INVOICE_REQUEST_CODE)
            }
            return@setOnMenuItemClickListener super.onOptionsItemSelected(it)
        }

        binding.addNote.setOnClickListener {
            if (binding.note.text.isNullOrEmpty()) {
                binding.note.error = Config.REQUIRED_FIELD
                binding.note.requestFocus()
            } else {
                notesList.add(
                    (notesList.size + 1).toString() + ". " + binding.note.text.toString()
                )

                val adapter = NoteListAdapter(this, notesList)
                binding.notesList.adapter = adapter
                binding.notesList.layoutManager = LinearLayoutManager(this)
                binding.notesList.setItemViewCacheSize(Config.LIST_CACHED_SIZE)

                binding.note.setText("")
            }
        }
        
        binding.addGood.setOnClickListener { 
            if (checkGoodsValidity()) {
                var gstRate = goodsGstRateString.toDouble()
                var quantity = goodsQuantityString.toDouble()
                var rate = goodsRateString.toDouble()
                var total = quantity * rate

                val goods = GoodUtils(
                    (goodsList.size + 1).toString(),
                    goodsName,
                    gstRate,
                    goodsMou,
                    quantity,
                    rate,
                    total
                )

                goodsList.add(goods)

                val adapter = GoodsAdapter(this, goodsList)
                binding.goodsList.adapter = adapter
                binding.goodsList.layoutManager = LinearLayoutManager(this)
                binding.goodsList.setItemViewCacheSize(Config.LIST_CACHED_SIZE)

                binding.goodsName.setText("")
                binding.gstRate.setText("")
                binding.mou.setText("")
                binding.quantity.setText("")
                binding.goodsRate.setText("")
            }
        }
    }

    private fun checkGoodsValidity(): Boolean {

        goodsName = binding.goodsName.text.toString()
        goodsGstRateString = binding.gstRate.text.toString()
        goodsMou = binding.mou.text.toString()
        goodsQuantityString = binding.quantity.text.toString()
        goodsRateString = binding.goodsRate.text.toString()

        if (goodsName.isNullOrEmpty()) {
            binding.goodsName.error = Config.REQUIRED_FIELD
            binding.goodsName.requestFocus()
            return false
        }
        
        if (goodsGstRateString.isNullOrEmpty()) {
            binding.gstRate.error = Config.REQUIRED_FIELD
            binding.gstRate.requestFocus()
            return false
        }
        
        if (goodsMou.isNullOrEmpty()) {
            binding.mou.error = Config.REQUIRED_FIELD
            binding.mou.requestFocus()
            return false
        }
        
        if (goodsQuantityString.isNullOrEmpty()) {
            binding.quantity.error = Config.REQUIRED_FIELD
            binding.quantity.requestFocus()
            return false
        }
        
        if (goodsRateString.isNullOrEmpty()) {
            binding.goodsRate.error = Config.REQUIRED_FIELD
            binding.goodsRate.requestFocus()
            return false
        }
        
        return true
    }

    private fun checkValidity(): Boolean {
        if (binding.invoiceNo.text.toString().isNullOrEmpty()) {
            binding.invoiceNo.error = Config.REQUIRED_FIELD
            binding.invoiceNo.requestFocus()
            return false
        }

        if (goodsList.size < 1) {
            binding.goodsName.error = Config.REQUIRED_FIELD
            binding.goodsName.requestFocus()
            return false
        }

        if (notesList.size < 1) {
            binding.note.error = Config.REQUIRED_FIELD
            binding.note.requestFocus()
            return false
        }

        return true
    }

    override fun onResume() {
        super.onResume()
        val presenter = LedgerPresenterIml(this, this)
        presenter.fetchLedgerIdList()
        
        setViews()
    }

    private fun setViews() {

        val calendar = Calendar.getInstance()

        ecommerceDay = calendar.get(Calendar.DAY_OF_MONTH)
        invoiceDay = calendar.get(Calendar.DAY_OF_MONTH)
        shippingDay = calendar.get(Calendar.DAY_OF_MONTH)

        ecommerceMonth = calendar.get(Calendar.MONTH)
        invoiceMonth = calendar.get(Calendar.MONTH)
        shippingMonth = calendar.get(Calendar.MONTH)

        ecommerceYear = calendar.get(Calendar.YEAR)
        invoiceYear = calendar.get(Calendar.YEAR)
        shippingYear = calendar.get(Calendar.YEAR)

        ecommerceDateString = getFormattedDate(ecommerceDay, ecommerceMonth, ecommerceYear).toString()
        binding.eCommerceDate.text = ecommerceDateString

        invoiceDateString = getFormattedDate(invoiceDay, invoiceMonth, invoiceYear).toString()
        binding.invoiceDate.text = invoiceDateString

        shippingDateString = getFormattedDate(shippingDay, shippingMonth, shippingYear).toString()
        binding.shippingDate.text = shippingDateString

        binding.eCommerceDate.setOnClickListener {

            var datePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    ecommerceDay = dayOfMonth
                    ecommerceMonth = month
                    ecommerceYear = year
                    ecommerceDateString = getFormattedDate(ecommerceDay, ecommerceMonth, ecommerceYear).toString()
                    binding.eCommerceDate.text = ecommerceDateString
                }, ecommerceYear, ecommerceMonth, ecommerceDay)
            datePickerDialog.show()
        }
        
        binding.invoiceDate.setOnClickListener {

            var datePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    invoiceDay = dayOfMonth
                    invoiceMonth = month
                    invoiceYear = year
                    invoiceDateString = getFormattedDate(invoiceDay, invoiceMonth, invoiceYear).toString()
                    binding.invoiceDate.text = invoiceDateString
                }, invoiceYear, invoiceMonth, invoiceDay)
            datePickerDialog.show()
        }
        
        binding.shippingDate.setOnClickListener {

            var datePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    shippingDay = dayOfMonth
                    shippingMonth = month
                    shippingYear = year
                    shippingDateString = getFormattedDate(shippingDay, shippingMonth, shippingYear).toString()
                    binding.shippingDate.text = shippingDateString
                }, shippingYear, shippingMonth, shippingDay)
            datePickerDialog.show()
        }
    }



    private fun getFormattedDate(day: Int, month: Int, year: Int): CharSequence? {
        return "%02d/%02d/%04d".format(
            day,
            month+1,
            year
        )
    }


    override fun onFetchLedgerIdListListener(ledgerList: List<String>) {
        super.onFetchLedgerIdListListener(ledgerList)
        this.ledgerList = ledgerList as ArrayList<String>

        val adapter = ArrayAdapter<String>(
            this, android.R.layout.simple_list_item_1, this.ledgerList)

        binding.invoiceNo.setAdapter(adapter)

        val taxPayableTypes = arrayListOf<String>("Yes", "No")
        val taxAdapter = ArrayAdapter<String>(
            this, android.R.layout.simple_list_item_1, taxPayableTypes
        )
        binding.taxPayableOnReverseCharge.setAdapter(taxAdapter)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Log.d(TAG, "code: $requestCode, $permissions, $grantResults")
        if (requestCode == Config.INVOICE_REQUEST_CODE && grantResults.isNotEmpty()) {

            var cgstRateString = binding.cgstRate.text.toString()
            var sgstRateString = binding.sgstRate.text.toString()
            var igstRateString = binding.igstRate.text.toString()
            var roundingDifferenceString = binding.roundingDifference.text.toString()
            var cgstRate = 0.0
            var sgstRate = 0.0
            var igstRate = 0.0
            var roundingDifference = 0.0

            if (!cgstRateString.isNullOrEmpty())
                cgstRate = cgstRateString.toDouble()
            if (!sgstRateString.isNullOrEmpty())
                sgstRate = sgstRateString.toDouble()
            if (!igstRateString.isNullOrEmpty())
                igstRate = igstRateString.toDouble()
            if (!roundingDifferenceString.isNullOrEmpty())
                roundingDifference = roundingDifferenceString.toDouble()



            var serialNo = ""
            var goods = ""
            var hsnSACCode = ""
            var gstRate = ""
            var mou = ""
            var quality = ""
            var rate = ""
            var amount = ""
            var notes = "Notes\n"

            hsnSACCode = binding.goodsHscSacCode.text.toString()

            notesList.forEach {
                notes += "  $it\n"
            }

            var totalAmount = 0.0
            var totalRate = 0.0
            var totalQuantity = 0.0

            goodsList.forEach {
                serialNo += "${it.serialNo}\n"
                goods += "${it.name}\n"
                gstRate += "${it.gstRate}%\n"
                mou += "${it.mou}\n"
                quality += "${it.quantity}\n"
                rate += "${it.rate}\n"
                amount += "${it.total}\n"
                totalAmount += it.total
                totalRate += totalRate
                totalQuantity += it.quantity
            }

            var cgstAmount = totalAmount * (cgstRate / 100)
            var sgstAmount = totalAmount * (sgstRate / 100)
            var igstAmount = totalAmount * (igstRate / 100)

            var finalAmount = totalAmount + cgstAmount + sgstAmount + igstAmount - roundingDifference

            val invoiceBuilder = InvoiceBuilder(this)
            val invoice = InvoiceUtils(
                binding.invoiceNo.text.toString(),
                invoiceDateString,
                getValue(binding.terms.text.toString()),
                getValue(binding.placeOfSupply.text.toString()),
                getValue(binding.shippingBillNo.text.toString()),
                shippingDateString,
                getValue(binding.transporterName.text.toString()),
                getValue(binding.llRRNo.text.toString()),
                getValue(binding.eCommerce.text.toString()),
                ecommerceDateString,
                getValue(binding.sellerName.text.toString()),
                binding.sellerAddress1.text.toString(),
                binding.sellerAddress2.text.toString(),
                binding.sellerAddress3.text.toString(),
                binding.sellerAddress4.text.toString(),
                getValue(binding.sellerStateCode.text.toString()),
                getValue(binding.sellerPAN.text.toString()),
                getValue(binding.sellerGSTIN.text.toString()),
                getValue(binding.buyerName.text.toString()),
                binding.buyerAddress1.text.toString(),
                binding.buyerAddress2.text.toString(),
                binding.buyerAddress3.text.toString(),
                binding.buyerAddress4.text.toString(),
                getValue(binding.buyerStateCode.text.toString()),
                getValue(binding.buyerPAN.text.toString()),
                getValue(binding.buyerGSTIN.text.toString()),
                getValue(binding.taxPayableOnReverseCharge.text.toString()),
                getValue(binding.goodsHscSacCode.text.toString()),
                "$cgstRate",
                cgstAmount.toString(),
                "$sgstRate",
                sgstAmount.toString(),
                "$igstRate",
                igstAmount.toString(),
                "-$roundingDifference",
                totalRate.toString(),
                totalAmount.toString(),
                finalAmount.toString(),
                totalQuantity.toString(),
                getValue(binding.bankName.text.toString()),
                getValue(binding.currentAccount.text.toString()),
                getValue(binding.ifsc.text.toString())
            )
            if (invoiceBuilder.createPdf(
                    invoice,
                    serialNo,
                    goods,
                    hsnSACCode,
                    gstRate,
                    mou,
                    quality,
                    rate,
                    amount,
                    notes
                )){
//                onBackPressed()
            }
        }
    }

    private fun getValue(value: String?): String {
        return if (value.isNullOrEmpty())
            "N.A."
        else
            value
    }

    // Toolbar menu setting
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.done_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setUpToolbar() {

        binding.toolbar.title = Config.TOOLBAR_TITLE_CREATE_INVOICE

        binding.toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.d(TAG, "Back pressed")
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft)
    }

    private fun getValues() {
        invoiceName = Config.INVOICE_NAME + Calendar.getInstance().timeInMillis
    }



}