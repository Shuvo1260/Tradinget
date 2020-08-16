package org.binaryitplanet.tradinget.Features.View.Invoice

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import org.binaryitplanet.tradinget.Features.Adapter.GoodsAdapter
import org.binaryitplanet.tradinget.Features.Adapter.NoteListAdapter
import org.binaryitplanet.tradinget.Features.Prsenter.InvoicePresenterIml
import org.binaryitplanet.tradinget.Features.Prsenter.LedgerPresenterIml
import org.binaryitplanet.tradinget.Features.View.Ledger.ViewLedgers
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.*
import org.binaryitplanet.tradinget.databinding.ActivityCreateInvoiceBinding
import java.math.RoundingMode
import java.util.*
import kotlin.collections.ArrayList

class CreateInvoice : AppCompatActivity(), ViewLedgers, InvoiceSettingsView {
    private val TAG = "CreateInvoice"
    private lateinit var binding: ActivityCreateInvoiceBinding

    // Variables

    private var goodsList = arrayListOf<GoodUtils>()
//    private var ledgerList = arrayListOf<String>()
    
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
    private lateinit var pdfPath: String

    private lateinit var ledger: LedgerUtils
    private lateinit var buyer: StakeholderUtils
    private lateinit var invoiceSettings: InvoiceSettingsUtils
    private var notesList = arrayListOf<NotesUtils>()

    private var gstRateEstimation: Double = 0.0
    private var gstRateTotal: Double = 0.0
    private var totalPacketAmount: Double = 0.0

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

                gstRateTotal += gstRate
                totalPacketAmount += total
                gstRateEstimation = (totalPacketAmount * (gstRateTotal / 100)).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()

                binding.gstRateEstimation.text = "GST amount: $gstRateEstimation"
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

        if (notesList.size <= 0) {
            Toast.makeText(
                this,
                Config.INVOICE_SETTINGS_FAILED_MESSAGE,
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        if (binding.invoiceNo.text.toString().isNullOrEmpty()){
            binding.invoiceNo.error = Config.REQUIRED_FIELD
            binding.invoiceNo.requestFocus()
            return false
        }

        if (binding.stateType.text.toString().isNullOrEmpty()){
            binding.stateType.error = Config.REQUIRED_FIELD
            binding.stateType.requestFocus()
            return false
        }

        if (goodsList.size < 1) {
            binding.goodsName.error = Config.REQUIRED_FIELD
            binding.goodsName.requestFocus()
            return false
        }

        return true
    }

    override fun onResume() {
        super.onResume()

        ledger = intent?.getSerializableExtra(Config.LEDGER) as LedgerUtils
        buyer = intent?.getSerializableExtra(Config.STAKEHOLDER) as StakeholderUtils

        val presenter = InvoicePresenterIml(this, this)
        presenter.fetchInvoiceSettings()
        
        setViews()
    }

    override fun onFetchInvoiceSettingsListener(
        invoiceSEttings: InvoiceSettingsUtils,
        noteList: List<NotesUtils>
    ) {
        super.onFetchInvoiceSettingsListener(invoiceSEttings, noteList)
        invoiceSettings = invoiceSEttings
        notesList = noteList as ArrayList<NotesUtils>
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
        ecommerceDateString = "N.A."

        invoiceDateString = getFormattedDate(invoiceDay, invoiceMonth, invoiceYear).toString()
        binding.invoiceDate.text = invoiceDateString

        shippingDateString = getFormattedDate(shippingDay, shippingMonth, shippingYear).toString()
        binding.shippingDate.text = shippingDateString
        shippingDateString = "N.A."

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

        var stateTypes = arrayListOf(Config.SAME_STATE, Config.INTER_STATE)
        var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, stateTypes)
        binding.stateType.setAdapter(adapter)

        var taxTypes = arrayListOf(Config.YES_MESSAGE, Config.NO_MESSAGE)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, taxTypes)
        binding.taxPayableOnReverseCharge.setAdapter(adapter)
    }



    private fun getFormattedDate(day: Int, month: Int, year: Int): CharSequence? {
        return "%02d/%02d/%04d".format(
            day,
            month+1,
            year
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Log.d(TAG, "code: $requestCode, $permissions, $grantResults")
        if (requestCode == Config.INVOICE_REQUEST_CODE && grantResults.isNotEmpty()) {

            var roundingDifferenceString = binding.roundingDifference.text.toString()
            var cgstRate = 0.0
            var sgstRate = 0.0
            var igstRate = 0.0
            var roundingDifference = 0.0

            var stateType = binding.stateType.text.toString().trim()

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
            var totalGSTRate = 0.0

            hsnSACCode = invoiceSettings.hsnNumber!!

            notesList.forEach {
                notes += "  ${it.note}\n"
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
                totalGSTRate = it.gstRate
            }

            totalGSTRate = totalGSTRate.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()

            if (stateType == Config.SAME_STATE) {
                cgstRate = (totalGSTRate / 2).toBigDecimal().setScale(3, RoundingMode.UP).toDouble()
                sgstRate = (totalGSTRate / 2).toBigDecimal().setScale(3, RoundingMode.UP).toDouble()
            } else {
                igstRate = totalGSTRate.toBigDecimal().setScale(3, RoundingMode.UP).toDouble()
            }

            var cgstAmount = (totalAmount * (cgstRate / 100))
                .toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
            var sgstAmount = (totalAmount * (sgstRate / 100))
                .toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
            var igstAmount = (totalAmount * (igstRate / 100))
                .toBigDecimal().setScale(2, RoundingMode.UP).toDouble()

            var finalAmount = (totalAmount + cgstAmount + sgstAmount + igstAmount - roundingDifference)
                .toBigDecimal().setScale(2, RoundingMode.UP).toDouble()

            // Buyer details starts
            var buyerAddressLength = buyer.address?.length!!
            var buyerAddress = buyer.address!!
            var buyerAddresses = arrayListOf<String>()

            if (buyerAddressLength >= 30) {
                buyerAddresses.add(buyerAddress.substring(0, 29))
            } else {
                buyerAddresses.add(buyerAddress.substring(0, buyerAddressLength-1))
                Log.d(TAG, "Length: ${buyerAddresses[0]} ")
            }
            if (buyerAddressLength >= 60) {
                buyerAddresses.add(buyerAddress.substring(30, 59))
            } else if (buyerAddressLength in 31..59) {
                buyerAddresses.add(buyerAddress.substring(30, buyerAddressLength-1))
            } else {
                buyerAddresses.add("")
            }
            if (buyerAddressLength >= 90) {
                buyerAddresses.add(buyerAddress.substring(60, 89))
            } else if (buyerAddressLength in 61..89) {
                buyerAddresses.add(buyerAddress.substring(60, buyerAddressLength-1))
            } else {
                buyerAddresses.add("")
            }
            if (buyerAddressLength >= 120) {
                buyerAddresses.add(buyerAddress.substring(90, 89))
            } else if (buyerAddressLength in 91..119) {
                buyerAddresses.add(buyerAddress.substring(90, buyerAddressLength-1))
            } else {
                buyerAddresses.add("")
            }


            val invoiceBuilder = InvoiceBuilder(this)
            val invoice = InvoiceUtils(
                ledger.ledgerId,
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
                invoiceSettings.name,
                invoiceSettings.address1,
                invoiceSettings.address2,
                invoiceSettings.address3,
                invoiceSettings.address4,
                invoiceSettings.stateCode,
                invoiceSettings.panNumber,
                invoiceSettings.gstNumber,
                buyer.name,
                buyerAddresses[0],
                buyerAddresses[1],
                buyerAddresses[2],
                buyerAddresses[3],
                buyer.stateCode,
                buyer.panNumber,
                buyer.gstNumber,
                getValue(binding.taxPayableOnReverseCharge.text.toString()),
                getValue(hsnSACCode),
                "$cgstRate",
                cgstAmount.toString(),
                "$sgstRate",
                sgstAmount.toString(),
                "$igstRate",
                igstAmount.toString(),
                "-$roundingDifference",
                totalRate.toString(),
                totalAmount.toString(),
                AmountToWordsConverter.convert(finalAmount.toInt()),
                finalAmount.toString(),
                totalQuantity.toString(),
                invoiceSettings.bankNameAndBrunch,
                invoiceSettings.bankAccount,
                invoiceSettings.bankIFSC,
                invoiceSettings.imageUrl
            )
            pdfPath = invoiceBuilder.createPdf(
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
            )

            if (!pdfPath.isNullOrEmpty()) {

                ledger.invoicePath = pdfPath
                val presenter = LedgerPresenterIml(this, this)
                presenter.updateLedger(ledger)
            }
        }
    }


    override fun onLedgerUpdateListener(status: Boolean) {
        super.onLedgerUpdateListener(status)
        Log.d(TAG, "UpdateStatus: $status")
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



}