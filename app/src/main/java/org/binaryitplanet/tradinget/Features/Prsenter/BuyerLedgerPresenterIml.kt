package org.binaryitplanet.tradinget.Features.Prsenter

import android.content.Context
import android.util.Log
import org.binaryitplanet.tradinget.Features.Model.DatabaseManager
import org.binaryitplanet.tradinget.Features.View.Ledger.TransactionView
import org.binaryitplanet.tradinget.Utils.BuyerLedgerUtils
import org.binaryitplanet.tradinget.Utils.LedgerUtils
import java.lang.Exception

class BuyerLedgerPresenterIml(
    private val context: Context,
    private val transactionView: TransactionView
): BuyerLedgerPresenter {

    private val TAG = "BuyerLedgerPresenter"

    override fun insertBuyerLedger(ledger: LedgerUtils, buyerLedger: BuyerLedgerUtils) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            databaseManager.getBuyerLedgerDAO().insert(buyerLedger)

            databaseManager.getLedgerDAO().update(ledger)
            transactionView.onInsertLedgerListener(true)
        }catch (e: Exception) {
            Log.d(TAG, "InsertBuyerLedgerError: ${e.message}")
            transactionView.onInsertLedgerListener(false)
        }
    }

    override fun deleteBuyerLedger(ledger: LedgerUtils, buyerLedger: BuyerLedgerUtils) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            databaseManager.getBuyerLedgerDAO().delete(buyerLedger)

            databaseManager.getLedgerDAO().update(ledger)
            transactionView.onDeleteLedgerListener(true)
        }catch (e: Exception) {
            Log.d(TAG, "DeleteBuyerLedgerError: ${e.message}")
            transactionView.onDeleteLedgerListener(false)
        }
    }

    override fun fetchBuyerLedger(ledgerId: Long, type: Boolean) {

        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val ledgerList = databaseManager.getBuyerLedgerDAO().fetchBuyerLedgerByIdAndType(
                ledgerId,
                type
            )
            if (type)
                transactionView.onFetchBrokerLedgerListener(ledgerList)
            else
                transactionView.onFetchLedgerListener(ledgerList)
        }catch (e: Exception) {
            Log.d(TAG, "FetchBuyerLedgerError: ${e.message} Type: $type")
        }
    }
}