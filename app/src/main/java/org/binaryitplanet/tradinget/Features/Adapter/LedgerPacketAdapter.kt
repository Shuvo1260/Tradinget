package org.binaryitplanet.tradinget.Features.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_ledger_packet_list_item.view.*
import kotlinx.android.synthetic.main.view_list_item_packet.view.weight
import org.binaryitplanet.tradinget.Features.View.Ledger.ViewLedgers
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.SoldPacketUtils

class LedgerPacketAdapter(
    val context: Context,
    val packetList: ArrayList<SoldPacketUtils>,
    val viewLedger: ViewLedgers
): RecyclerView.Adapter<LedgerPacketAdapter.ViewHolder>() {
    private val TAG = "PacketAdapter"
    // Holding the view

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(
                    R.layout.view_ledger_packet_list_item,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int = packetList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var view = holder.itemView

        view.packet.text = "Packet: " + packetList[position].packetNumber
        view.subPacket.text = "Sub packet: " + packetList[position].packetDetailsNumber

        view.weight.text = packetList[position].weight.toString() + " " + Config.CTS
        view.rate.text = packetList[position].rate.toString() + " " + Config.RUPEE_SIGN

        view.delete.setOnClickListener {
            viewLedger.onPacketDeleteListener(position)
        }
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {}
}