package org.binaryitplanet.tradinget.Features.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.view_packets_drowp_down.view.*
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.PacketDetailsUtils
import org.binaryitplanet.tradinget.Utils.PacketUtils


class SubPacketDropDownAdapter(
    context: Context,
    private val packetList: ArrayList<PacketDetailsUtils>
): ArrayAdapter<PacketDetailsUtils>(context, 0, packetList) {

    private val TAG = "SubPacketDropDownAdapter"

    private fun initView(position: Int, convertView: View?, viewGroup: ViewGroup): View {

        var view: View
        Log.d(TAG, "ConvertedView")

        if (convertView == null) {
            view =  LayoutInflater.from(context).inflate(
                R.layout.view_packets_drowp_down, viewGroup, false
            )
        } else {
            view = convertView
        }

        if (getItem(position)!= null) {
            view?.packetId?.text = packetList[position].packetDetailsNumber
            view?.packetName?.text = packetList[position].sieve
        }

        return view!!
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }


    override fun getCount(): Int = packetList.size

    override fun getItem(position: Int): PacketDetailsUtils? {
        Log.d(TAG, "PacketPosition: $position")
        return packetList[position]
    }

}