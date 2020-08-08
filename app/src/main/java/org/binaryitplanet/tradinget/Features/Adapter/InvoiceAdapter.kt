package org.binaryitplanet.tradinget.Features.Adapter

import android.content.Context
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_invoice_list_item.view.*
import org.binaryitplanet.tradinget.Features.View.Invoice.InvoiceBuilder
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config

class InvoiceAdapter(
    val context: Context,
    val invoices: Array<String>
): RecyclerView.Adapter<InvoiceAdapter.ViewHolder>() {
    private val TAG = "StakeholderAdapter"
    // Holding the view

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(
                    R.layout.view_invoice_list_item,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int = invoices.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.itemView

        view.invoiceName.text = invoices[position].substring(
            0, invoices[position].length - 4
        )

        view.setOnClickListener {
            val invoiceBuilder = InvoiceBuilder(context)
            invoiceBuilder.printPDF(
                Config.PDF_DIR_PATH + invoices[position]
            )
        }
    }
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {}
}