package org.binaryitplanet.rentalreminderapp.Features.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_seller_ledger_list_item.view.*
import org.binaryitplanet.tradinget.Features.View.Ledger.SellerLedgerView
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.SellerLedgerUtils

class SellerLedgerListAdapter(
    val context: Context,
    val ledgerList: ArrayList<SellerLedgerUtils>,
    val isNew: Boolean,
    var listener: SellerLedgerView
):  RecyclerView.Adapter<SellerLedgerListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SellerLedgerListAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(
                    R.layout.view_seller_ledger_list_item,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int = ledgerList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.itemView
        val months = context.resources.getStringArray(R.array.months)
        view.paymentType.text = ledgerList[position].paymentType
        view.monthYear.text = months[ledgerList[position].month] + ", " +
                ledgerList[position].year
        view.date.text = ledgerList[position].date
        view.amount.text = ledgerList[position].amount.toString()
        view.rupeeSign.text = context.resources.getString(R.string.rupeeSign)
        view.remark.text = ledgerList[position].remark

        if (ledgerList[position].transactionType == "Debit") {
            view.amount.setTextColor(
                ContextCompat.getColor(context, R.color.red)
            )
            view.rupeeSign.setTextColor(
                ContextCompat.getColor(context, R.color.red)
            )
        }

        view.delete.visibility = View.VISIBLE

        view.delete.setOnClickListener {
            listener.onSellerLedgerDeleteClick(position)
        }
        if (position == ledgerList.size -1) {
            view.bottomMargin.visibility = View.VISIBLE
        }
    }

    // Holding the view
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {}

}