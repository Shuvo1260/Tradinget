package org.binaryitplanet.tradinget.Features.View.Invoice

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.InvoiceUtils
import org.binaryitplanet.tradinget.databinding.ActivityCreateInvoiceBinding
import java.util.*

class CreateInvoice : AppCompatActivity() {
    private val TAG = "CreateInvoice"
    private lateinit var binding: ActivityCreateInvoiceBinding

    // Variables
    private lateinit var invoiceName: String

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_invoice)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_invoice)

        setUpToolbar()

        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.done) {

                val permissions:Array<String> = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permissions, Config.INVOICE_REQUEST_CODE)
            }
            return@setOnMenuItemClickListener super.onOptionsItemSelected(it)
        }
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
                "123456",
                "09/08/2020",
                "10",
                "SURAT",
                "N.A.",
                "N.A.",
                "BY HAND TO HAND",
                "N.A",
                "N.A.",
                "N.A.",
                "Sharee brahmani gems",
                "1",
                "2",
                "3",
                "4",
                "24",
                "SellerPan",
                "SellerGSTIN",
                "HARSH DIAM",
                "Ad1",
                "Ad2",
                "Ad3",
                "Ad4",
                "24",
                "BuyerPan",
                "BuyerGSTIN",
                "No",
                "243234342",
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
                "Bank",
                "53264545",
                "2423432"
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