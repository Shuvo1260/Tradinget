package org.binaryitplanet.tradinget.Features.View.Ledger

import org.binaryitplanet.tradinget.Utils.BuyerLedgerUtils

interface TransactionView {
    fun onInsertLedgerListener(status: Boolean){}
    fun onDeleteLedgerListener(status: Boolean){}
    fun onFetchLedgerListener(ledger: List<BuyerLedgerUtils>){}
    fun onFetchBrokerLedgerListener(brokerLedger: List<BuyerLedgerUtils>){}

    fun onLedgerDeleteClickListener(position: Int, type: Boolean){}
}