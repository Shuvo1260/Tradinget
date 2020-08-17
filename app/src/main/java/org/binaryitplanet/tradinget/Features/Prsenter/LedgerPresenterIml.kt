package org.binaryitplanet.tradinget.Features.Prsenter

import android.content.Context
import android.util.Log
import org.binaryitplanet.tradinget.Features.Model.DatabaseManager
import org.binaryitplanet.tradinget.Features.View.Ledger.ViewLedgers
import org.binaryitplanet.tradinget.Utils.LedgerUtils
import org.binaryitplanet.tradinget.Utils.PacketDetailsUtils
import org.binaryitplanet.tradinget.Utils.PacketUtils
import org.binaryitplanet.tradinget.Utils.SoldPacketUtils
import java.lang.Exception

class LedgerPresenterIml(
    private val context: Context,
    private val viewLedgers: ViewLedgers
): LedgerPresenter {

    private val TAG = "LedgerPresenter"

    override fun insertLedger(
        ledgerUtils: LedgerUtils,
        soldPacketList: ArrayList<SoldPacketUtils>,
        packetList: ArrayList<PacketUtils>,
        subPacketList: ArrayList<PacketDetailsUtils>
    ) {
        var packetFlag = arrayListOf<Boolean>()
        var subPacketFlag = arrayListOf<Boolean>()
        packetList.forEach {
            packetFlag.add(false)
        }
        subPacketList.forEach {
            subPacketFlag.add(false)
        }
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val id = databaseManager.getLedgerDAO().insert(ledgerUtils)

            if (id > 0) {
                Log.d(TAG, "PacketSize: ${packetList.size}")
                Log.d(TAG, "SubPacketSize: ${subPacketList.size}")
                soldPacketList.forEach {
                    databaseManager.getSoldPacketDAO().insert(it)
                    packetFlag[it.packetIndex] = true

                    packetList[it.packetIndex].weight -= it.weight
                    packetList[it.packetIndex].price -= (it.weight * packetList[it.packetIndex].rate)
//                    subPacketList[it.subPacketIndex].weight -= it.weight
                    if (it.subPacketIndex != -1) {
                        subPacketFlag[it.subPacketIndex] = true
                        subPacketList[it.subPacketIndex].soldWeight += it.weight
                        subPacketList[it.subPacketIndex].remainingWeight -= it.weight
                    }
                }

                for (index in 0 until packetList.size) {
                    Log.d(TAG, "PacketIndex: $index")
                    if (packetFlag[index]) {
                        databaseManager.getPacketDAO()
                            .update(packetList[index])
                    }
                }

                for (index in 0 until subPacketList.size) {
                    Log.d(TAG, "SubPacketIndex: $index")
                    if (subPacketFlag[index]){
                        databaseManager.getPacketDetailsDAO()
                            .update(subPacketList[index])
                    }
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

            var packetList = databaseManager.getPacketDAO()
                .getPacketList() as ArrayList<PacketUtils>
            var subPacketList = databaseManager.getPacketDetailsDAO()
                .getAllPacketDetailsList() as ArrayList<PacketDetailsUtils>

            var soldPacketList = databaseManager.getSoldPacketDAO()
                .getSoldPacketListByLedgerId(ledgerUtils.ledgerId!!)
            soldPacketList.forEach {soldIt->
                packetList.forEach {
                    if (it.packetNumber == soldIt.packetNumber
                        && it.packetName == soldIt.packetName) {
                        it.weight += soldIt.weight
                        it.price += (soldIt.weight * it.rate)

                        databaseManager.getPacketDAO().update(it)
                    }

                }
                if (soldIt.subPacketIndex != -1) {
                    subPacketList.forEach {
                        if (soldIt.packetDetailsNumber == it.packetDetailsNumber
                            && it.packetNumber == soldIt.packetNumber) {
                            it.soldWeight -= soldIt.weight
                            it.remainingWeight += soldIt.weight
                            databaseManager.getPacketDetailsDAO()
                                .update(it)
                        }
                    }
                }
            }
            if (id > 0) {
                databaseManager.getSoldPacketDAO()
                    .deleteAllSoldPacketsByLedgerId(ledgerUtils.ledgerId!!)
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

    override fun updateLedger(
        ledgerUtils: LedgerUtils,
        soldPacketList: ArrayList<SoldPacketUtils>
    ) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val id = databaseManager.getLedgerDAO().update(ledgerUtils)

            if (id > 0) {
                databaseManager.getSoldPacketDAO().deleteAllSoldPacketsByLedgerId(ledgerUtils.ledgerId!!)
                soldPacketList.forEach{
                    databaseManager.getSoldPacketDAO().insert(it)
                }
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

    override fun fetchLedgerById(id: String) {
        try{

            val databaseManager = DatabaseManager.getInstance(context)!!
            val ledger = databaseManager.getLedgerDAO()
                .getLedgerByLedgerId(id)
            viewLedgers.onFetchLedger(ledger)
        }catch (e: Exception){
            Log.d(TAG, "FetchLedgerError: ${e.message}")
        }
    }

    override fun fetchLedgerListByStakeholderId(id: Long) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val ledgerList = databaseManager.getLedgerDAO()
                .getLedgerListByStakeholderId(id)
            viewLedgers.onFetchLedgerListListener(ledgerList)
        }catch (e: Exception){
            Log.d(TAG, "FetchLedgerListError: ${e.message}")
        }
    }

    override fun fetchLedgerListByBrokerId(id: Long) {

        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val ledgerList = databaseManager.getLedgerDAO()
                .getLedgerListByBrokerId(id)
            viewLedgers.onFetchLedgerListListener(ledgerList)
        }catch (e: Exception){
            Log.d(TAG, "FetchLedgerListError: ${e.message}")
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

    override fun fetchSoldPacketList(isOrderName: Boolean) {

        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            if (isOrderName) {
                val soldPacketList = databaseManager.getSoldPacketDAO()
                    .getSoldPacketListOrderByName()
                viewLedgers.onFetchSoldPacketListListener(soldPacketList)
            } else {
                val soldPacketList = databaseManager.getSoldPacketDAO()
                    .getSoldPacketListOrderByDate()
                viewLedgers.onFetchSoldPacketListListener(soldPacketList)
            }
        } catch (e: Exception) {
            Log.d(TAG, "FetchSoldPacketListByError: ${e.message}")
        }
    }

    override fun fetchLedgerIdList() {

        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val ledgerIdList = databaseManager.getLedgerDAO()
                .getLedgerIDList()
            viewLedgers.onFetchLedgerIdListListener(ledgerIdList)
        } catch (e: Exception) {
            Log.d(TAG, "FetchLedgerIdListByError: ${e.message}")
        }
    }

    override fun fetchPendingLedgerList() {

        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val ledgerList = databaseManager.getLedgerDAO()
                .getPendingLedgerList()
            viewLedgers.onFetchLedgerListListener(ledgerList)
        } catch (e: Exception) {
            Log.d(TAG, "FetchPendingLedgerListError: ${e.message}")
        }
    }
}