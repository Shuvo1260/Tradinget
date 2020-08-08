package org.binaryitplanet.tradinget.Features.View.Ledger

import org.binaryitplanet.tradinget.Utils.LedgerUtils


interface ViewLedgers {
    fun onPacketDeleteListener(position: Int){}
    fun onLedgerInsertListener(status: Boolean){}
    fun onLedgerDeleteListener(status: Boolean){}
    fun onLedgerUpdateListener(status: Boolean){}

    fun onFetchLedgerListListener(ledgerList: List<LedgerUtils>){}
}