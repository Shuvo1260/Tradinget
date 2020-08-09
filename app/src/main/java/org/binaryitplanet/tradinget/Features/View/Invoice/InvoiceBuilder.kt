package org.binaryitplanet.tradinget.Features.View.Invoice

import android.content.Context
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.Log
import com.itextpdf.text.*
import com.itextpdf.text.pdf.*
import org.binaryitplanet.tradinget.Features.Adapter.InvoicePrintAdapter
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.InvoiceUtils
import java.io.File
import java.io.FileOutputStream

class InvoiceBuilder(
    val context: Context
) {

    private val TAG = "InvoiceBuilder"

    // PDF creating section starts
    fun createPdf(
        invoice: InvoiceUtils,
        serialNo: String,
        goods: String,
        hsnSACCode: String,
        gstRate: String,
        mou: String,
        quality: String,
        rate: String,
        amount: String,
        notes: String
    ): Boolean {

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
            // Preparing document
            var invoicePath = File(file, invoice.ledgerId + Config.PDF_FORMAT)

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
                "Seller name: " + invoice.sellerName,
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
                8f,
                BaseColor.BLACK,
                BaseColor.WHITE,
                Element.ALIGN_LEFT,
                5.0f
            ))
            invoiceDetailsTable.addCell(getCell(
                invoice.ledgerId,
                FontFactory.TIMES,
                8f,
                BaseColor.BLACK,
                BaseColor.WHITE,
                Element.ALIGN_CENTER,
                5.0f
            ))
            invoiceDetailsTable.addCell(getCell(
                "Date of Invoice",
                FontFactory.TIMES_BOLD,
                8f,
                BaseColor.BLACK,
                BaseColor.WHITE,
                Element.ALIGN_CENTER,
                5.0f
            ))
            invoiceDetailsTable.addCell(getCell(
                invoice.invoiceDate,
                FontFactory.TIMES,
                8f,
                BaseColor.BLACK,
                BaseColor.WHITE,
                Element.ALIGN_CENTER,
                5.0f
            ))


            // Second row
            invoiceDetailsTable.addCell(getCell(
                "Terms :",
                FontFactory.TIMES_BOLD,
                8f,
                BaseColor.BLACK,
                BaseColor.WHITE,
                Element.ALIGN_LEFT,
                5.0f
            ))
            invoiceDetailsTable.addCell(getCell(
                invoice.terms,
                FontFactory.TIMES,
                8f,
                BaseColor.BLACK,
                BaseColor.WHITE,
                Element.ALIGN_CENTER,
                5.0f
            ))
            invoiceDetailsTable.addCell(getCell(
                "Place of Supply",
                FontFactory.TIMES_BOLD,
                8f,
                BaseColor.BLACK,
                BaseColor.WHITE,
                Element.ALIGN_CENTER,
                5.0f
            ))
            invoiceDetailsTable.addCell(getCell(
                "SURAT",
                FontFactory.TIMES,
                8f,
                BaseColor.BLACK,
                BaseColor.WHITE,
                Element.ALIGN_CENTER,
                5.0f
            ))

            firstTable.addCell(invoiceDetailsTable)


            firstTable.addCell(
                getCell(
                    invoice.sellerAddress1 + "\n"
                    + invoice.sellerAddress2 + "\n"
                    + invoice.sellerAddress3 + "\n"
                    + invoice.sellerAddress4 + "\n"
                    + "STATE CODE: " + invoice.sellerStateCode,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_LEFT,
                    5.0f
                )
            )

            // Shipping details table start
            var shippingTable = PdfPTable(4)
            shippingTable.widthPercentage = 100F
            shippingTable.setWidths(floatArrayOf(5f, 5f, 2.5f, 2.5f))
            shippingTable.defaultCell.border = Rectangle.NO_BORDER
            shippingTable.defaultCell.setPadding(0f)

            // Shipping table first row
            shippingTable.addCell(
                getCell(
                    "Shipping Bill No. :",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_LEFT,
                    5.0f
                )
            )
            shippingTable.addCell(
                getCell(
                    invoice.shippingBillNo,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_LEFT,
                    5.0f
                )
            )
            shippingTable.addCell(
                getCell(
                    "Date",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )
            shippingTable.addCell(
                getCell(
                    invoice.shippingDate,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )

            // Second row starts
            shippingTable.addCell(
                getCell(
                    "Transporter Name:",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_LEFT,
                    5.0f
                )
            )
            shippingTable.addCell(
                getCell(
                    invoice.transporterName,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_LEFT,
                    5.0f
                )
            )
            shippingTable.addCell(
                getCell(
                    " ",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )
            shippingTable.addCell(
                getCell(
                    " ",
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )


            // Third row starts
            shippingTable.addCell(
                getCell(
                    "LR/RR No.",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )
            shippingTable.addCell(
                getCell(
                    invoice.lrRRNo,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_LEFT,
                    5.0f
                )
            )
            shippingTable.addCell(
                getCell(
                    " ",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )
            shippingTable.addCell(
                getCell(
                    " ",
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )

            // Fourth row starts
            shippingTable.addCell(
                getCell(
                    "E-Commerce Party:",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )
            shippingTable.addCell(
                getCell(
                    invoice.eCommerceParty,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )
            shippingTable.addCell(
                getCell(
                    "Date",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )
            shippingTable.addCell(
                getCell(
                    invoice.eCommercePartyDate,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )


            firstTable.addCell(shippingTable)
            
            // Third row starts
            firstTable.addCell(
                getCell(
                    "SELLER'S DETAILS:",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_LEFT,
                    5.0f
                )
            )

            firstTable.addCell(
                getCell(
                    "BUYER'S DETAILS:",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_LEFT,
                    5.0f
                )
            )


            firstTable.addCell(
                getCell(
                    "PAN: " + invoice.sellerPan + "\n"
                    +    "GSTIN: " + invoice.sellerGSTIN,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_LEFT,
                    5.0f
                )
            )

            firstTable.addCell(
                getCell(
                    "PAN: " + invoice.buyerPan + "\n"
                    + "GSTIN: " + invoice.buyerGSTIN,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_LEFT,
                    5.0f
                )
            )

            firstTable.addCell(
                getCell(
                    "BUYER: BILL TO PARTY",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_LEFT,
                    5.0f
                )
            )

            firstTable.addCell(
                getCell(
                    "SHIP TO PARTY",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_LEFT,
                    5.0f
                )
            )

            firstTable.addCell(
                getCell(
                    invoice.buyerName + "\n"
                    + invoice.buyerAddress1 + "\n"
                    + invoice.buyerAddress2 + "\n"
                    + invoice.buyerAddress3 + "\n"
                    + invoice.buyerAddress4 + "\n"
                    + "STATE CODE: " + invoice.buyerStateCode,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_LEFT,
                    5.0f
                )
            )

            firstTable.addCell(
                getCell(
                    invoice.buyerName + "\n"
                    + invoice.buyerAddress1 + "\n"
                    + invoice.buyerAddress2 + "\n"
                    + invoice.buyerAddress3 + "\n"
                    + invoice.buyerAddress4 + "\n"
                    + "STATE CODE: " + invoice.buyerStateCode,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_LEFT,
                    5.0f
                )
            )

            firstTable.addCell(
                getCell(
                    " ",
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_LEFT,
                    5.0f
                )
            )

            // Tax payable table starts
            var taxPayableTable = PdfPTable(2)
            taxPayableTable.widthPercentage = 100F
            taxPayableTable.setWidths(floatArrayOf(7f, 3f))

            taxPayableTable.addCell(
                getCell(
                    "Tax is Payable on Reverse Charge: ",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_LEFT,
                    5.0f
                )
            )
            taxPayableTable.addCell(
                getCell(
                    invoice.taxIsPayableOnReverseCharge,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_LEFT,
                    5.0f
                )
            )

            firstTable.addCell(taxPayableTable)

            invoiceTable.addCell(firstTable)


            // first table starts
            var goodsTable = PdfPTable(9)
            goodsTable.widthPercentage = 100F
            goodsTable.setWidths(floatArrayOf(0.39f, 3.8f, 0.83f, 0.83f, 0.83f, 0.83f, 0.83f, 0.83f, 0.83f))


            goodsTable.addCell(
                getCell(
                    "Sr. No.",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )
            goodsTable.addCell(
                getCell(
                    "Description of Goods/Services",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_LEFT,
                    5.0f
                )
            )
            goodsTable.addCell(
                getCell(
                    " ",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_LEFT,
                    5.0f
                )
            )
            goodsTable.addCell(
                getCell(
                    "HSN/SAC Code",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )
            goodsTable.addCell(
                getCell(
                    "GST Rate[%]",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )
            goodsTable.addCell(
                getCell(
                    "MOU",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )
            goodsTable.addCell(
                getCell(
                    "Quality",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )
            goodsTable.addCell(
                getCell(
                    "Rate",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )
            goodsTable.addCell(
                getCell(
                    "Amount",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )

            // Goods table first row start

            // Goods table second row

            goodsTable.addCell(
                getCell(
                    serialNo,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )
            goodsTable.addCell(
                getCell(
                    goods,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_LEFT,
                    5.0f
                )
            )
            goodsTable.addCell(
                getCell(
                    "",
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )
            goodsTable.addCell(
                getCell(
                    hsnSACCode,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )
            goodsTable.addCell(
                getCell(
                    gstRate,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )
            goodsTable.addCell(
                getCell(
                    mou,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )
            goodsTable.addCell(
                getCell(
                    quality,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )
            goodsTable.addCell(
                getCell(
                    rate,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )
            goodsTable.addCell(
                getCell(
                    amount,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )
            // Goods second row ends

            // Goods third row starts

            var serve = "TOTAL\nCGST\nSGST\nIGST\nROUNDING DIFFERENCES"
            var rates = "\n" + invoice.cgstRate + "%\n" +
                    invoice.sgstRate + "%\n" + invoice.igstRate + "%\n"

            var amounts = invoice.totalAmount + "\n" +
                    invoice.cgstAmount + "\n" + invoice.sgstAmount +
                    "\n" + invoice.igstAmount + "\n" + invoice.roundingDifference


            goodsTable.addCell(
                getCell(
                    "",
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )
            goodsTable.addCell(
                getCell(
                    serve,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )
            goodsTable.addCell(
                getCell(
                    rates,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )
            goodsTable.addCell(
                getCell(
                    "",
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )
            goodsTable.addCell(
                getCell(
                    "",
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )
            goodsTable.addCell(
                getCell(
                    "",
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )
            goodsTable.addCell(
                getCell(
                    "",
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )
            goodsTable.addCell(
                getCell(
                    "",
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )
            goodsTable.addCell(
                getCell(
                    amounts,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )

            // Third row ends

            invoiceTable.addCell(goodsTable)
            // Invoice details ends


            var goodsFooter = PdfPTable(4)
            goodsFooter.widthPercentage = 100F
            goodsFooter.setWidths(
                floatArrayOf(7.51f, 0.83f, 0.83f, 0.83f)
            )


            goodsFooter.addCell(
                getCell(
                    "***** Net Bill Amount *****",
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )
            goodsFooter.addCell(
                getCell(
                    invoice.totalQuality,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )
            goodsFooter.addCell(
                getCell(
                    " ",
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )
            goodsFooter.addCell(
                getCell(
                    invoice.finalAmount,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )

            invoiceTable.addCell(goodsFooter)

            document.add(invoiceTable)
            // Invoice table ends

            // Tax table starts


            var taxTable = PdfPTable(9)
            taxTable.widthPercentage = 100F
            taxTable.spacingBefore = 10f
            taxTable.setWidths(floatArrayOf(0.39f, 3.80f, 0.83f, 0.83f, 0.83f, 0.83f, 0.83f, 0.83f, 0.83f))

            taxTable.addCell(
                getCell(
                    "Sr. No.",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )
            taxTable.addCell(
                getCell(
                    "HSN/SAC CODE",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )
            taxTable.addCell(
                getCell(
                    "Taxable Value",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )
            taxTable.addCell(
                getCell(
                    "CGST Rate",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )
            taxTable.addCell(
                getCell(
                    "CGST Amount",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )
            taxTable.addCell(
                getCell(
                    "SGST Rate",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )
            taxTable.addCell(
                getCell(
                    "SGST Amount",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )
            taxTable.addCell(
                getCell(
                    "IGST Rate",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )
            taxTable.addCell(
                getCell(
                    "IGST Amount",
                    FontFactory.TIMES_BOLD,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.LIGHT_GRAY,
                    Element.ALIGN_CENTER,
                    5.0f
                )
            )



            // Tax Second row starts
            taxTable.addCell(
                getCell(
                    "1",
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )

            taxTable.addCell(
                getCell(
                    invoice.hsnSACCode,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_LEFT,
                    5.0f
                )
            )

            taxTable.addCell(
                getCell(
                    invoice.finalAmount,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )

            taxTable.addCell(
                getCell(
                    invoice.cgstRate + "%",
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )

            taxTable.addCell(
                getCell(
                    invoice.cgstAmount,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )

            taxTable.addCell(
                getCell(
                    invoice.sgstRate + "%",
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )

            taxTable.addCell(
                getCell(
                    invoice.sgstAmount,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )

            taxTable.addCell(
                getCell(
                    invoice.igstRate + "%",
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )

            taxTable.addCell(
                getCell(
                    invoice.igstAmount,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_RIGHT,
                    5.0f
                )
            )

            document.add(taxTable)

            // Tax table ends

            // Bank details starts
            var bankTitle = "Our Bank Details:"

            var bankTitleFont = Font(fontName, 8f, Font.BOLD, BaseColor.BLACK)
            addNewItem(document, bankTitle, Element.ALIGN_LEFT, bankTitleFont)

            var bankText =
                    "    Bank Name & Branch: " + invoice.bankNameAndBrunch + "\n" +
                    "    Cur. A/c No. : " + invoice.currentAccount + "\n" +
                    "    IFSC: " + invoice.ifsc


            var bankFont = Font(fontName, 8f, Font.NORMAL, BaseColor.BLACK)
            addNewItem(document, bankText, Element.ALIGN_LEFT, bankFont)
            // Bank details ends

            // Notes table complete
            var notesTable = PdfPTable(1)
            notesTable.widthPercentage = 100F
            notesTable.spacingBefore = 10f

            notesTable.addCell(
                getCell(
                    notes,
                    FontFactory.TIMES,
                    8f,
                    BaseColor.BLACK,
                    BaseColor.WHITE,
                    Element.ALIGN_LEFT,
                    5.0f
                )
            )

            document.add(notesTable)

            document.close()

            printPDF(invoicePath.absolutePath)

            return true

        } catch (e: Exception) {
            Log.d(TAG, "Exception: ${e.message}")
            return false
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