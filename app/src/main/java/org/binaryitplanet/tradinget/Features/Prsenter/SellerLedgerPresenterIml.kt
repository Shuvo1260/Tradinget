package org.binaryitplanet.tradinget.Features.Prsenter

import android.content.Context
import android.util.Log
import org.binaryitplanet.tradinget.Features.Model.DatabaseManager
import org.binaryitplanet.tradinget.Features.View.Ledger.SellerLedgerView
import org.binaryitplanet.tradinget.Utils.SellerLedgerUtils
import java.lang.Exception

class SellerLedgerPresenterIml(
    private val context: Context,
    private val sellerLedgerView: SellerLedgerView
): SellerLedgerPresenter {

    private val TAG = "SellerLedgerPresenter"

    override fun insertLedger(ledger: SellerLedgerUtils) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val id = databaseManager.getSellerLedgerDAO().insert(ledger)
            if (id > 0) {
                sellerLedgerView.onInsertLedgerListener(true)
            }
        } catch (e: Exception){
            Log.d(TAG, "InsertSellerLedgerError: ${e.message}")
            sellerLedgerView.onInsertLedgerListener(false)
        }
    }

    override fun deleteLedger(ledger: SellerLedgerUtils) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val id = databaseManager.getSellerLedgerDAO().delete(ledger)
            if (id > 0) {
                sellerLedgerView.onDeleteLedgerListener(true)
            }
        } catch (e: Exception){
            Log.d(TAG, "InsertSellerLedgerError: ${e.message}")
            sellerLedgerView.onDeleteLedgerListener(false)
        }
    }

    override fun updateLedger(ledger: SellerLedgerUtils) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val id = databaseManager.getSellerLedgerDAO().update(ledger)
            if (id > 0) {
                sellerLedgerView.onUpdateLedgerListener(true)
            }
        } catch (e: Exception){
            Log.d(TAG, "InsertSellerLedgerError: ${e.message}")
            sellerLedgerView.onUpdateLedgerListener(false)
        }
    }

    override fun fetchLedgerListById(id: Long) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val ledgerList = databaseManager.getSellerLedgerDAO()
                .fetchLedgerById(id)
            sellerLedgerView.onFetchLedgerListListener(ledgerList)

        } catch (e: Exception){
            Log.d(TAG, "InsertSellerLedgerError: ${e.message}")
        }
    }
}