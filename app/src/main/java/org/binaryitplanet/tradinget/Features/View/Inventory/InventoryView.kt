package org.binaryitplanet.tradinget.Features.View.Inventory

import org.binaryitplanet.tradinget.Utils.PacketUtils

interface InventoryView {
    fun onSavePacketListener(status: Boolean){}
    fun onUpdatePacketListener(status: Boolean){}
    fun onDeletePacketListener(status: Boolean){}
    fun onFetchPacketListListener(PacketList: List<PacketUtils>){}
    fun onFetchPacketListener(Packet: PacketUtils){}
}