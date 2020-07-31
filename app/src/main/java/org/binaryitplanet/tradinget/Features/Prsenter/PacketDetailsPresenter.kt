package org.binaryitplanet.tradinget.Features.Prsenter

import org.binaryitplanet.tradinget.Utils.PacketDetailsUtils

interface PacketDetailsPresenter {
    fun insertPacketDetails(packetDetails: PacketDetailsUtils)
    fun deletePacketDetails(packetDetails: PacketDetailsUtils)
    fun updatePacketDetails(packetDetails: PacketDetailsUtils)
    fun fetchPacketDetailsById(id: Long)
    fun fetchPacketDetailsList(packetId: String)
}