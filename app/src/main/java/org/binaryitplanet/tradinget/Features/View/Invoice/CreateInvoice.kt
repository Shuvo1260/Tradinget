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


            var serialNo = "1\n2"
            var goods = "Cut & Polished \nDiamond"
            var hsnSACCode = "24323243\n4343243"
            var gstRate = "0.25\n0.35"
            var mou = "Carats\nTola"
            var quality = "3.95\n3.45"
            var rate = "1,000.00\n2,000.00"
            var amount = "3.950.00\n3,450,00"
            var notes = "Notes\n 1. sfdlkjsdf"

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
                "0.125",
                "4.94",
                "0.125",
                "4.94",
                "0.000",
                "-",
                "-9.88",
                "3.95",
                "3,950.0",
                "3,950.0",
                "3.95",
                getValue(binding.bankName.text.toString()),
                getValue(binding.currentAccount.text.toString()),
                getValue(binding.ifsc.text.toString())"
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