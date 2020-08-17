package org.binaryitplanet.tradinget.Features.Prsenter

import org.binaryitplanet.tradinget.Utils.LedgerUtils
import org.binaryitplanet.tradinget.Utils.PacketDetailsUtils
import org.binaryitplanet.tradinget.Utils.PacketUtils
import org.binaryitplanet.tradinget.Utils.SoldPacketUtils

interface LedgerPresenter {
    fun insertLedger(
        ledgerUtils: LedgerUtils,
        soldPacketList: ArrayList<SoldPacketUtils>,
        packetList: ArrayList<PacketUtils>,
        subPacketList: ArrayList<PacketDetailsUtils>
    )
    fun deleteLedger(ledgerUtils: LedgerUtils)
    fun updateLedger(ledgerUtils: LedgerUtils, soldPacketList: ArrayList<SoldPacketUtils>)
    fun updateLedger(ledgerUtils: LedgerUtils)
    fun fetchLedgerById(id: String)
    fun fetchLedgerListByStakeholderId(id: Long)
    fun fetchLedgerListByBrokerId(id: Long)

    fun fetchSoldPacketListByLedgerId(ledgerId: String)

    fun fetchSoldPacketList()

    fun fetchLedgerIdList()


    fun fetchPendingLedgerList()
}