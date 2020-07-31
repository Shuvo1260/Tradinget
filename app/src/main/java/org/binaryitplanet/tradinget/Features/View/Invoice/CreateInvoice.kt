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
                createPdf()
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



    // PDF creating section starts
    private fun createPdf() {

        try {

            getValues()

            // Preparing path
            var storagePath = Config.PDF_DIR_PATH
            var file = File(storagePath)
            if (!file.exists()) {

                Log.d(TAG, "PathNotExist: ${file.absolutePath}")
                if (file.mkdirs())
                    Log.d(TAG, "PathSuccess: ${file.absolutePath}")
                else
                    Log.d(TAG, "PathFailed: ${file.absolutePath}")
            }

            // Preparing document
            var invoicePath = File(file, invoiceName + Config.PDF_FORMAT)

            var document = Document(PageSize.A4)

            var fileOutputStream = FileOutputStream(invoicePath.absolutePath)

            PdfWriter.getInstance(document, fileOutputStream)

            document.open()

            document.addCreationDate()
            document.addAuthor(Config.INVOICE_AUTHOR)
            document.addCreator(Config.INVOICE_CREATOR)

            var colorAccent = BaseColor(0, 153, 204, 255)
//            var fontSize = 20.0f
            var valueFontSize = 26.0f

            var fontName = BaseFont.createFont(
                FontFactory.TIMES_ROMAN, "UTF-8", BaseFont.EMBEDDED)

            var titleFont = Font(fontName, Config.TITLE_FONT_SIZE, Font.NORMAL, BaseColor.BLACK)
            addNewItem(document, "Order details", Element.ALIGN_CENTER, titleFont)


            var orderFont = Font(fontName, Config.FONT_SIZE, Font.NORMAL, colorAccent)
            addNewItem(document, "Order No", Element.ALIGN_LEFT, orderFont)

            // preparing the table
            var invoiceTable = PdfPTable(2)
            invoiceTable.widthPercentage = 100F
            invoiceTable.setWidths(floatArrayOf(5f, 5f))
            invoiceTable.spacingBefore = 30.0f


            invoiceTable.addCell(getHeaderCell("Seller name: Shuvo"))
            invoiceTable.addCell(getHeaderCell("Seller name: Shuvo"))
            invoiceTable.addCell(getHeaderCell("Seller name: Shuvo"))


            var newTable = PdfPTable(3)
            newTable.widthPercentage = 100F
            newTable.setWidths(floatArrayOf(3f, 3f, 4f))
            newTable.spacingBefore = 30.0f


            newTable.addCell(getHeaderCell("Buyer name: Shuvo"))
            newTable.addCell(getHeaderCell("Buyer name: Shuvo"))
            newTable.addCell(getHeaderCell("Buyer name: Shuvo"))
            newTable.addCell(getHeaderCell("Buyer name: Shuvo"))
            newTable.addCell(getHeaderCell("Buyer name: Shuvo"))
            newTable.addCell(getHeaderCell("Buyer name: Shuvo"))

            invoiceTable.addCell(newTable)


            document.add(invoiceTable)



            document.close()

            printPDF(invoicePath.absolutePath)

        } catch (e: Exception) {
            Log.d(TAG, "Exception: ${e.message}")
        }



    }
    // PDF creation ends

    // PDF table design section starts

    fun getHeaderCell(text: String?): PdfPCell? {
        val fs = FontSelector()
        val font = FontFactory.getFont(FontFactory.TIMES_BOLD, 11f)
        font.color = BaseColor.BLACK
        fs.addFont(font)
        val phrase = fs.process(text)
        val cell = PdfPCell(phrase)
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.setPadding(5.0f)
        cell.backgroundColor = BaseColor.GRAY
        return cell
    }

    private fun printPDF(path: String) {
        var printManager = getSystemService(Context.PRINT_SERVICE) as PrintManager

        try {
            var invoicePrintAdapter =
                InvoicePrintAdapter(
                    this,
                    path
                )
            printManager.print(
                "Invoice",
                invoicePrintAdapter,
                PrintAttributes.Builder().build()
            )
        }catch (e: Exception) {
            Log.d(TAG, "PrintingException: ${e.message}")
        }
    }

    private fun addNewItem(document: Document, text: String, align: Int, font: Font) {
        var chunk = Chunk(text, font)
        var paragraph = Paragraph(chunk)
        paragraph.alignment = align
        document.add(paragraph)
    }
}