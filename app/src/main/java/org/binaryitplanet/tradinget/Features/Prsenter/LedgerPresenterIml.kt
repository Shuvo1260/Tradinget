package org.binaryitplanet.tradinget.Features.Prsenter

import android.content.Context
import android.util.Log
import org.binaryitplanet.tradinget.Features.Model.DatabaseManager
import org.binaryitplanet.tradinget.Features.View.Ledger.ViewLedgers
import org.binaryitplanet.tradinget.Utils.LedgerUtils
import org.binaryitplanet.tradinget.Utils.SoldPacketUtils
import java.lang.Exception

class LedgerPresenterIml(
    private val context: Context,
    private val viewLedgers: ViewLedgers
): LedgerPresenter {

    private val TAG = "LedgerPresenter"

    override fun insertLedger(
        ledgerUtils: LedgerUtils,
        soldPacketList: ArrayList<SoldPacketUtils>
    ) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val id = databaseManager.getLedgerDAO().insert(ledgerUtils)

            if (id > 0) {
                soldPacketList.forEach {
                    databaseManager.getSoldPacketDAO().insert(it)
                }
                viewLedgers.onLedgerInsertListener(true)
            }
            else
                viewLedgers.onLedgerInsertListener(false)
            Log.d(TAG, "Saving $id: $ledgerUtils")
        }catch (e: Exception){
            Log.d(TAG, "InsertLedgerError: ${e.message}")
            viewLedgers.onLedgerInsertListener(false)
        }
    }

    override fun deleteLedger(ledgerUtils: LedgerUtils) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val id = databaseManager.getLedgerDAO().delete(ledgerUtils)

            val soldPacketList = databaseManager
                .getSoldPacketDAO()
                .getSoldPacketListByLedgerId(ledgerUtils.ledgerId!!) as ArrayList<SoldPacketUtils>
            if (id > 0) {
                soldPacketList.forEach {
                    databaseManager.getSoldPacketDAO().delete(it)
                }
                viewLedgers.onLedgerDeleteListener(true)
            }
            else
                viewLedgers.onLedgerDeleteListener(false)
            Log.d(TAG, "Deleting $id: $ledgerUtils")
        }catch (e: Exception){
            Log.d(TAG, "DeleteLedgerError: ${e.message}")
            viewLedgers.onLedgerDeleteListener(false)
        }
    }

    override fun updateLedger(ledgerUtils: LedgerUtils) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val id = databaseManager.getLedgerDAO().update(ledgerUtils)

            if (id > 0) {
                viewLedgers.onLedgerUpdateListener(true)
            }
            else
                viewLedgers.onLedgerUpdateListener(false)
            Log.d(TAG, "Saving $id: $ledgerUtils")
        }catch (e: Exception){
            Log.d(TAG, "UpdateLedgerError: ${e.message}")
            viewLedgers.onLedgerUpdateListener(false)
        }
    }

    override fun fetchLedgerListByStakeholderId(id: Long) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val ledgerList = databaseManager.getLedgerDAO()
                .getLedgerListByStakeholderId(id)
            viewLedgers.onFetchLedgerListListener(ledgerList)
        }catch (e: Exception){
            Log.d(TAG, "InsertLedgerError: ${e.message}")
        }
    }

    override fun fetchLedgerListByBrokerId(id: Long) {

        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val ledgerList = databaseManager.getLedgerDAO()
                .getLedgerListByBrokerId(id)
            viewLedgers.onFetchLedgerListListener(ledgerList)
        }catch (e: Exception){
            Log.d(TAG, "InsertLedgerError: ${e.message}")
        }
    }

    override fun fetchSoldPacketListByLedgerId(ledgerId: String) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val soldPacketList = databaseManager.getSoldPacketDAO()
                .getSoldPacketListByLedgerId(ledgerId)
            viewLedgers.onFetchSoldPacketListListener(soldPacketList)
        } catch (e: Exception) {
            Log.d(TAG, "FetchSoldPacketListByLedgerIdError: ${e.message}")
        }
    }

    override fun fetchSoldPacketList() {

        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val soldPacketList = databaseManager.getSoldPacketDAO()
                .getSoldPacketList()
            viewLedgers.onFetchSoldPacketListListener(soldPacketList)
        } catch (e: Exception) {
            Log.d(TAG, "FetchSoldPacketListByError: ${e.message}")
        }
    }
}