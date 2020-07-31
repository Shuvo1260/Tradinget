package org.binaryitplanet.tradinget.Features.Adapter

import android.content.Context
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.util.Log
import org.binaryitplanet.tradinget.Utils.Config
import java.io.*
import java.lang.Exception

class InvoicePrintAdapter(var context: Context, var path: String): PrintDocumentAdapter() {

    private val TAG = "PDFDocumentAdapter"

    override fun onLayout(
        oldAttributes: PrintAttributes?,
        newAttributes: PrintAttributes?,
        cancellationSignal: CancellationSignal?,
        callback: LayoutResultCallback?,
        extras: Bundle?
    ) {
        if (cancellationSignal?.isCanceled!!)
            callback?.onLayoutCancelled()
        else {
            var printDocumentBuilder = PrintDocumentInfo.Builder(Config.INVOICE_NAME)
            printDocumentBuilder.setContentType(
                PrintDocumentInfo.CONTENT_TYPE_DOCUMENT
            ).setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN)
                .build()
            callback?.onLayoutFinished(
                printDocumentBuilder.build(),
                !newAttributes?.equals(oldAttributes)!!
            )
        }
    }

    override fun onWrite(
        pages: Array<out PageRange>?,
        destination: ParcelFileDescriptor?,
        cancellationSignal: CancellationSignal?,
        callback: WriteResultCallback?
    ) {
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null

        try {
            var file = File(path)

            inputStream = FileInputStream(file)
            outputStream = FileOutputStream(
                destination?.fileDescriptor
            )

            var byteArray: ByteArray = ByteArray(16284)
            var size: Int
            size = inputStream.read(byteArray)
            while (size >= 0 &&
                    !cancellationSignal?.isCanceled!!) {
                outputStream.write(byteArray, 0, size)
                size = inputStream.read(byteArray)
            }

            if (cancellationSignal?.isCanceled!!)
                callback?.onWriteCancelled()
            else
                callback?.onWriteFinished(
                    arrayOf(PageRange.ALL_PAGES)
                )

        }catch (e: Exception) {
            Log.d(TAG, "PrintingAdapterException: ${e.message}")
            callback?.onWriteFailed(e.message)
        } finally {
            try {
                inputStream?.close()
                outputStream?.close()
            }catch (e: Exception) {
                Log.d(TAG, "InputOutputStreamCloseException: ${e.message}")
            }
        }
    }

}
