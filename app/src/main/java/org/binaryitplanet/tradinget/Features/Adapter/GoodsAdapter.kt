package org.binaryitplanet.tradinget.Features.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_due_dates_list.view.*
import kotlinx.android.synthetic.main.view_list_goods_item.view.*
import kotlinx.android.synthetic.main.view_list_note_item.view.*
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.GoodUtils
import org.binaryitplanet.tradinget.Utils.LedgerUtils

class GoodsAdapter(
    val context: Context,
    val goodsList: ArrayList<GoodUtils>
): RecyclerView.Adapter<GoodsAdapter.ViewHolder>() {
    private val TAG = "goodsListAdapter"
    // Holding the view

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(
                    R.layout.view_list_goods_item,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int = goodsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var view = holder.itemView

        view.name.text = goodsList[position].name
        view.gstRate.text = goodsList[position].gstRate.toString()
        view.mou.text = goodsList[position].mou
        view.quality.text = goodsList[position].quantity.toString()
        view.rate.text = goodsList[position].rate.toString()
        view.amount.text = goodsList[position].total.toString()
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {}
}