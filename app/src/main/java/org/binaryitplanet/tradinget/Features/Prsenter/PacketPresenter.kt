package org.binaryitplanet.tradinget.Features.Prsenter

import org.binaryitplanet.tradinget.Utils.PacketUtils

interface PacketPresenter {
    fun insertPacket(packet: PacketUtils)
    fun deletePacket(packet: PacketUtils)
    fun updatePacket(packet: PacketUtils)
    fun fetchPacketById(id: Long)
    fun fetchPacketList()
}