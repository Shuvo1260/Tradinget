package org.binaryitplanet.tradinget.Features.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.view_packets_drowp_down.view.*
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.PacketUtils
import org.binaryitplanet.tradinget.Utils.StakeholderUtils


class BrokerDropDownAdapter(
    context: Context,
    private val brokerList: ArrayList<StakeholderUtils>
): ArrayAdapter<StakeholderUtils>(context, 0, brokerList) {

    private val TAG = "BrokerDropDownAdapter"

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
            view?.packetId?.text = brokerList[position].mobileNumber
            view?.packetName?.text = brokerList[position].name
        }

        return view!!
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }


    override fun getCount(): Int = brokerList.size

    override fun getItem(position: Int): StakeholderUtils? {
        Log.d(TAG, "PacketPosition: $position")
        return brokerList[position]
    }

}