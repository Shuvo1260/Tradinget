package org.binaryitplanet.tradinget.Features.Prsenter

import org.binaryitplanet.tradinget.Utils.PacketDetailsUtils
import org.binaryitplanet.tradinget.Utils.PacketUtils

interface PacketDetailsPresenter {
    fun insertPacketDetails(packet: PacketUtils, packetDetails: PacketDetailsUtils)
    fun deletePacketDetails(packetDetails: PacketDetailsUtils)
    fun updatePacketDetails(packetDetails: PacketDetailsUtils)
    fun fetchPacketDetailsById(id: Long)
    fun fetchPacketDetailsList(packetId: String)
    fun fetchAllPacketDetailsList()
}