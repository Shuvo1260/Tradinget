package org.binaryitplanet.tradinget.Features.Prsenter

import org.binaryitplanet.tradinget.Utils.LedgerUtils
import org.binaryitplanet.tradinget.Utils.SoldPacketUtils

interface LedgerPresenter {
    fun insertLedger(ledgerUtils: LedgerUtils, soldPacketList: ArrayList<SoldPacketUtils>)
    fun deleteLedger(ledgerUtils: LedgerUtils)
    fun updateLedger(ledgerUtils: LedgerUtils)
    fun fetchLedgerListByStakeholderId(id: Long)
    fun fetchLedgerListByBrokerId(id: Long)
}