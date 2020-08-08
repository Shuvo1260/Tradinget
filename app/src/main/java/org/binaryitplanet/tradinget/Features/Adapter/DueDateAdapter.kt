package org.binaryitplanet.tradinget.Features.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_due_dates_list.view.*
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.LedgerUtils

class DueDateAdapter(
    val context: Context,
    val ledgerList: ArrayList<LedgerUtils>
): RecyclerView.Adapter<DueDateAdapter.ViewHolder>() {
    private val TAG = "DueDateAdapter"
    // Holding the view

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(
                    R.layout.view_due_dates_list,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int = ledgerList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var view = holder.itemView

        view.ledgerId.text = "ID: " + ledgerList[position].ledgerId

        view.dueDate.text = "Due date: " + ledgerList[position].dueDate
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {}
}