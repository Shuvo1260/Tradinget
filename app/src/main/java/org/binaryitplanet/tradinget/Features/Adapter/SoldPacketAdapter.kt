package org.binaryitplanet.tradinget.Features.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_sold_packet_item_view.view.*
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.SoldPacketUtils

class SoldPacketAdapter(
    val context: Context,
    val packetList: ArrayList<SoldPacketUtils>
): RecyclerView.Adapter<SoldPacketAdapter.ViewHolder>() {
    private val TAG = "PacketAdapter"
    // Holding the view

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(
                    R.layout.view_sold_packet_item_view,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int = packetList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var view = holder.itemView

        view.ledgerId.text = "Ledger id: " + packetList[position].ledgerId
        view.packetName.text = "Packet name: " + packetList[position].packetName

        view.packetNumber.text = "Packet: " + packetList[position].packetNumber
        view.subPacketNumber.text = "Sub packet: " + packetList[position].packetDetailsNumber

        view.weight.text = packetList[position].weight.toString() + " " + Config.CTS
        view.rate.text = Config.RUPEE_SIGN + " " + packetList[position].rate.toString()
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {}
}