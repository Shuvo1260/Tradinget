package org.binaryitplanet.tradinget.Features.Prsenter

import org.binaryitplanet.tradinget.Utils.BuyerLedgerUtils
import org.binaryitplanet.tradinget.Utils.LedgerUtils

interface BuyerLedgerPresenter {
    fun insertBuyerLedger(ledger: LedgerUtils, buyerLedger: BuyerLedgerUtils)
    fun deleteBuyerLedger(ledger: LedgerUtils, buyerLedger: BuyerLedgerUtils)
    fun fetchBuyerLedger(ledgerId: Long, type: Boolean)
}