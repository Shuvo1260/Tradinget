package org.binaryitplanet.tradinget.Features.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_due_dates_list.view.*
import kotlinx.android.synthetic.main.view_list_note_item.view.*
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.LedgerUtils

class NoteListAdapter(
    val context: Context,
    val noteList: ArrayList<String>
): RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {
    private val TAG = "NoteListAdapter"
    // Holding the view

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(
                    R.layout.view_list_note_item,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int = noteList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var view = holder.itemView

        view.note.text = noteList[position]
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {}
}