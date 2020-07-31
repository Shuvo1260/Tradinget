package org.binaryitplanet.tradinget.Features.Adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_list_item_packet.view.*
import kotlinx.android.synthetic.main.view_list_item_packet.view.packetNumber
import kotlinx.android.synthetic.main.view_list_item_packet.view.weight
import kotlinx.android.synthetic.main.view_list_item_packet_details.view.*
import org.binaryitplanet.tradinget.Features.View.Buyer.ViewBuyer
import org.binaryitplanet.tradinget.Features.View.Inventory.AddPacketDetails
import org.binaryitplanet.tradinget.Features.View.Inventory.ViewPacket
import org.binaryitplanet.tradinget.Features.View.Inventory.ViewPacketDetails
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.PacketDetailsUtils
import org.binaryitplanet.tradinget.Utils.PacketUtils
import android.util.Pair as UtilPair

class PacketDetailsAdapter(
    val context: Context,
    val viewPacketDetails: ViewPacketDetails,
    val packet: PacketUtils,
    val packetDetailsList: ArrayList<PacketDetailsUtils>
): RecyclerView.Adapter<PacketDetailsAdapter.ViewHolder>() {

    private val TAG = "PacketDetailsAdapter"
    // Holding the view

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(
                    R.layout.view_list_item_packet_details,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int = packetDetailsList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var view = holder.itemView

        view.packetNumber.text = packetDetailsList[position].packetDetailsNumber
        view.sieve.text = packetDetailsList[position].sieve

        view.weight.text = packetDetailsList[position].weight.toString() + " " + Config.CTS
        view.soldWeight.text = packetDetailsList[position].soldWeight.toString() + " " + Config.CTS
        view.remainingWeight.text = packetDetailsList[position].remainingWeight.toString() + " " + Config.CTS

        view.delete.setOnClickListener {
            viewPacketDetails.onDeleteClickListener(position)
        }

        view.setOnClickListener {
            Log.d(TAG, "PacketClicked: $position")

            var intent: Intent? = Intent(context, AddPacketDetails::class.java)

            // Passing selected item data
            intent!!.putExtra(Config.PACKET_DETAILS, packetDetailsList[position])
            intent!!.putExtra(Config.PACKET, packet)
            intent!!.putExtra(Config.OPERATION_FLAG, false)
            context.startActivity(intent)
        }
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {}
}