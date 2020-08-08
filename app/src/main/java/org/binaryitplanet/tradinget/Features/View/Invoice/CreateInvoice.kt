package org.binaryitplanet.tradinget.Features.View.Invoice

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.Log
import android.view.Menu
import androidx.databinding.DataBindingUtil
import com.itextpdf.text.*
import com.itextpdf.text.pdf.*
import org.binaryitplanet.tradinget.Features.Adapter.InvoicePrintAdapter
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.databinding.ActivityCreateInvoiceBinding
import java.io.File
import java.io.FileOutputStream
import java.util.*

class CreateInvoice : AppCompatActivity() {
    private val TAG = "CreateInvoice"
    private lateinit var binding: ActivityCreateInvoiceBinding

    // Variables
    private lateinit var invoiceName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_invoice)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_invoice)

        setUpToolbar()

        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.done) {
                val invoiceBuilder = InvoiceBuilder(this)
                invoiceBuilder.createPdf()
            }
            return@setOnMenuItemClickListener super.onOptionsItemSelected(it)
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