package org.binaryitplanet.tradinget.Features.View.Inventory

import org.binaryitplanet.tradinget.Utils.PacketDetailsUtils

interface ViewPacketDetails {
    fun onSavePacketDetailsListener(status: Boolean){}
    fun onUpdatePacketDetailsListener(status: Boolean){}
    fun onDeletePacketDetailsListener(status: Boolean){}
    fun onFetchPacketDetailsListListener(packetDetailsList: List<PacketDetailsUtils>){}
    fun onFetchPacketDetailsListener(packetDetails: PacketDetailsUtils){}
    fun onDeleteClickListener(position: Int){}

    fun onFetchAllPacketDetailsListListener(subPacketList: List<PacketDetailsUtils>){}
}