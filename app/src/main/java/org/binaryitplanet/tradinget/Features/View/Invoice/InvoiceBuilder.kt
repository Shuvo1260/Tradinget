package org.binaryitplanet.tradinget.Features.View.Invoice

import android.content.Context
import android.graphics.Color
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.Log
import com.itextpdf.text.*
import com.itextpdf.text.pdf.*
import org.binaryitplanet.tradinget.Features.Adapter.InvoicePrintAdapter
import org.binaryitplanet.tradinget.Utils.Config
import java.io.File
import java.io.FileOutputStream
import java.util.*

class InvoiceBuilder(
    val context: Context
) {

    private val TAG = "InvoiceBuilder"

    // PDF creating section starts
    fun createPdf() {

        try {
            // Preparing path
            var storagePath = Config.PDF_DIR_PATH
            var file = File(storagePath)
            Log.d(TAG, "PathNotExist: ${file.absolutePath}")
            if (!file.exists()) {

                Log.d(TAG, "PathNotExist: ${file.absolutePath}")
                if (file.mkdirs())
                    Log.d(TAG, "PathSuccess: ${file.absolutePath}")
                else
                    Log.d(TAG, "PathFailed: ${file.absolutePath}")
            }
            val invoiceName = Config.INVOICE_NAME + Calendar.getInstance().timeInMillis
            // Preparing document
            var invoicePath = File(file, invoiceName + Config.PDF_FORMAT)

            var document = Document(PageSize.A4)

            var fileOutputStream = FileOutputStream(invoicePath.absolutePath)

            PdfWriter.getInstance(document, fileOutputStream)

            document.open()

            document.addCreationDate()
            document.addAuthor(Config.INVOICE_AUTHOR)
            document.addCreator(Config.INVOICE_CREATOR)


            // Table section


            var fontName = BaseFont.createFont(
                FontFactory.TIMES_ROMAN, "UTF-8", BaseFont.EMBEDDED)

            var titleFont = Font(fontName, Config.TITLE_FONT_SIZE, Font.NORMAL, BaseColor.BLACK)
            addNewItem(document, "Invoice", Element.ALIGN_CENTER, titleFont)

            // preparing the table
            var invoiceTable = PdfPTable(1)
            invoiceTable.widthPercentage = 100F
            invoiceTable.spacingBefore = 30.0f
            invoiceTable.defaultCell.border = 0
            invoiceTable.defaultCell.setPadding(0f)



            // first table starts
            var firstTable = PdfPTable(2)
            firstTable.widthPercentage = 100F
            firstTable.setWidths(floatArrayOf(5f, 5f))

            // Seller name
            firstTable.addCell(getCell(
                "Seller name: Shuvo",
                FontFactory.TIMES_BOLD,
                13.0f,
                BaseColor.BLACK,
                BaseColor.LIGHT_GRAY,
                Element.ALIGN_LEFT,
                7.0f
            ))

            // Invoice details starts
            var invoiceDetailsTable = PdfPTable(4)
            invoiceDetailsTable.widthPercentage = 100F
            invoiceDetailsTable.setWidths(floatArrayOf(5f, 5f, 5f, 5f))
            invoiceDetailsTable.defaultCell.border = Rectangle.NO_BORDER
            invoiceDetailsTable.defaultCell.setPadding(0f)

            // First row
            invoiceDetailsTable.addCell(getCell(
                "Invoice No. :",
                FontFactory.TIMES_BOLD,
                10f,
                BaseColor.BLACK,
                BaseColor.WHITE,
                Element.ALIGN_LEFT,
                5.0f
            ))
            invoiceDetailsTable.addCell(getCell(
                "P/03/03",
                FontFactory.TIMES,
                10f,
                BaseColor.BLACK,
                BaseColor.WHITE,
                Element.ALIGN_CENTER,
                5.0f
            ))
            invoiceDetailsTable.addCell(getCell(
                "Date of Invoice",
                FontFactory.TIMES_BOLD,
                10f,
                BaseColor.BLACK,
                BaseColor.WHITE,
                Element.ALIGN_CENTER,
                5.0f
            ))
            invoiceDetailsTable.addCell(getCell(
                "07/08/2020",
                FontFactory.TIMES,
                10f,
                BaseColor.BLACK,
                BaseColor.WHITE,
                Element.ALIGN_CENTER,
                5.0f
            ))


            // Second row
            invoiceDetailsTable.addCell(getCell(
                "Terms :",
                FontFactory.TIMES_BOLD,
                10f,
                BaseColor.BLACK,
                BaseColor.WHITE,
                Element.ALIGN_LEFT,
                5.0f
            ))
            invoiceDetailsTable.addCell(getCell(
                "10",
                FontFactory.TIMES,
                10f,
                BaseColor.BLACK,
                BaseColor.WHITE,
                Element.ALIGN_CENTER,
                5.0f
            ))
            invoiceDetailsTable.addCell(getCell(
                "Place of Supply",
                FontFactory.TIMES_BOLD,
                10f,
                BaseColor.BLACK,
                BaseColor.WHITE,
                Element.ALIGN_CENTER,
                5.0f
            ))
            invoiceDetailsTable.addCell(getCell(
                "SURAT",
                FontFactory.TIMES,
                10f,
                BaseColor.BLACK,
                BaseColor.WHITE,
                Element.ALIGN_CENTER,
                5.0f
            ))

            firstTable.addCell(invoiceDetailsTable)




//            invoiceDetailsTable.addCell(getHeaderCell("Buyer name: Shuvo"))
//            invoiceDetailsTable.addCell(getHeaderCell("Buyer name: Shuvo"))
//            invoiceDetailsTable.addCell(getHeaderCell("Buyer name: Shuvo"))
//            invoiceDetailsTable.addCell(getHeaderCell("Buyer name: Shuvo"))
//            invoiceDetailsTable.addCell(getHeaderCell("Buyer name: Shuvo"))
//            invoiceDetailsTable.addCell(getHeaderCell("Buyer name: Shuvo"))

            invoiceTable.addCell(firstTable)
            // Invoice details ends

            document.add(invoiceTable)



            document.close()

            printPDF(invoicePath.absolutePath)

        } catch (e: Exception) {
            Log.d(TAG, "Exception: ${e.message}")
        }



    }
    // PDF creation ends

    // PDF table design section starts

//    fun getHeaderCell(text: String?): PdfPCell? {
//        val fs = FontSelector()
//        val font = FontFactory.getFont(FontFactory.TIMES_BOLD, 16f)
//        font.color = BaseColor.BLACK
//        fs.addFont(font)
//        val phrase = fs.process(text)
//
//        val cell = PdfPCell(phrase)
//        cell.horizontalAlignment = Element.ALIGN_CENTER
//        cell.setPadding(5.0f)
//        cell.backgroundColor = BaseColor.GRAY
//        return cell
//    }

    fun getCell(
        text: String?,
        fontName: String,
        fontSize: Float,
        fontColor: BaseColor,
        backgroundColor: BaseColor,
        align: Int,
        padding: Float
    ): PdfPCell? {
        val fs = FontSelector()
        val font = FontFactory.getFont(fontName, fontSize)
        font.color = fontColor
        fs.addFont(font)
        val phrase = fs.process(text)

        val cell = PdfPCell(phrase)
        cell.horizontalAlignment = align
        cell.setPadding(padding)
        cell.backgroundColor = backgroundColor
        return cell
    }

    fun printPDF(path: String) {
        var printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager

        try {
            var invoicePrintAdapter =
                InvoicePrintAdapter(
                    context,
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