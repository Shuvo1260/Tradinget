package org.binaryitplanet.tradinget.Features.View.Ledger

import org.binaryitplanet.tradinget.Utils.SellerLedgerUtils

interface SellerLedgerView {
    fun onInsertLedgerListener(status: Boolean){}
    fun onDeleteLedgerListener(status: Boolean){}
    fun onUpdateLedgerListener(status: Boolean){}
    fun onFetchLedgerListListener(ledgerList: List<SellerLedgerUtils>){}

    fun onSellerLedgerDeleteClick(position: Int){}
}