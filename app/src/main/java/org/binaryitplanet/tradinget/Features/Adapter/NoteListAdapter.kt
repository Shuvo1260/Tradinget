package org.binaryitplanet.tradinget.Features.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_list_note_item.view.*
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.NotesUtils

class NoteListAdapter(
    val context: Context,
    val noteList: ArrayList<NotesUtils>
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

        view.note.text = noteList[position].note
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {}
}