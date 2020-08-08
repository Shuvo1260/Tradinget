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
import org.binaryitplanet.tradinget.Features.View.Buyer.ViewBuyer
import org.binaryitplanet.tradinget.Features.View.Inventory.ViewPacket
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.PacketUtils
import android.util.Pair as UtilPair

class PacketAdapter(
    val context: Context,
    val packetList: ArrayList<PacketUtils>
): RecyclerView.Adapter<PacketAdapter.ViewHolder>() {
    private val TAG = "PacketAdapter"
    // Holding the view

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(
                    R.layout.view_list_item_packet,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int = packetList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var view = holder.itemView

        view.packetNumber.text = packetList[position].packetNumber
        view.packetName.text = packetList[position].packetName

        view.weight.text = packetList[position].weight.toString() + " " + Config.CTS
        view.price.text = packetList[position].price.toString() + " " + Config.RUPEE_SIGN



        view.setOnClickListener {
            Log.d(TAG, "PacketClicked: $position")

            var intent: Intent? = Intent(context, ViewPacket::class.java)


            // Passing selected item data
            intent!!.putExtra(Config.PACKET, packetList[position])

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // Creating animation options
                val options =
                    ActivityOptions
                        .makeSceneTransitionAnimation(
                            context as Activity,
                            UtilPair.create(
                                view.packetName,
                                context.resources.getString(R.string.packetNameTransition)
                            ),
                            UtilPair.create(
                                view.weight,
                                context.resources.getString(R.string.weightTransition)
                            ),
                            UtilPair.create(
                                view.price,
                                context.resources.getString(R.string.priceTransition)
                            )
                        )
                context.window.exitTransition = null
                context.startActivity(intent, options.toBundle())
            } else {
                context.startActivity(intent)
            }
        }
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {}
}