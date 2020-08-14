package org.binaryitplanet.tradinget.Features.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_pending_ledger_list_item.view.*
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.LedgerUtils
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

class PendingLedgerAdapter(
    val context: Context,
    val ledgerList: ArrayList<LedgerUtils>
): RecyclerView.Adapter<PendingLedgerAdapter.ViewHolder>() {
    private val TAG = "PacketAdapter"
    // Holding the view

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(
                    R.layout.view_pending_ledger_list_item,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int = ledgerList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var view = holder.itemView

        val time = Calendar.getInstance().timeInMillis

        view.ledgerId.text = "ID: " + ledgerList[position].ledgerId
        view.name.text = "Buyer name: " + ledgerList[position].stakeHolderName

        var dueDays = (ledgerList[position].dueDateInMilli - time) / (1440 * 60000)
        var dueTime = (ledgerList[position].dueDateInMilli - time) % (1440 * 60000)

//        if ((dueTime != 0L) and (dueDays < 0)) {
//            dueDays--
//        } else
        if ((dueTime != 0L) and (dueDays >= 0)) {
            dueDays++
        }

        view.dueDays.text = "Due days: $dueDays"

        view.totalAmount.text = "Total: " + Config.RUPEE_SIGN + " " + ledgerList[position].totalAmount.toString()
        view.dueAmount.text = "Due: " + Config.RUPEE_SIGN + " " +
                (ledgerList[position].totalAmount - ledgerList[position].paidAmount)

    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {}
}