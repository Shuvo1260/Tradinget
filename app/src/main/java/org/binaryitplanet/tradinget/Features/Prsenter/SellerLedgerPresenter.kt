package org.binaryitplanet.tradinget.Features.Prsenter

import org.binaryitplanet.tradinget.Utils.SellerLedgerUtils

interface SellerLedgerPresenter {
    fun insertLedger(ledger: SellerLedgerUtils)
    fun deleteLedger(ledger: SellerLedgerUtils)
    fun updateLedger(ledger: SellerLedgerUtils)
    fun fetchLedgerListBySellerId(sellerId: Long)
}