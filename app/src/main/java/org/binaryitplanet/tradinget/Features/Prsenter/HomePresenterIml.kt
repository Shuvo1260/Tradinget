package org.binaryitplanet.tradinget.Features.Prsenter

import android.content.Context
import android.util.Log
import org.binaryitplanet.tradinget.Features.Model.DatabaseManager
import org.binaryitplanet.tradinget.Features.View.Home.HomeView
import java.lang.Exception
import java.util.*

class HomePresenterIml(
    private val context: Context,
    private val homeView: HomeView
): HomePresenter {

    private val TAG = "HomePresenter"

    override fun fetchHomeItems() {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val month = Calendar.getInstance().get(Calendar.MONTH)
            val totalPayment = databaseManager.getLedgerDAO()
                .getTotalPaymentOfMonth(month)

            var dueAmount = 0.0

            val ledgerList = databaseManager.getLedgerDAO()
                .getDueLedgerList()
            ledgerList.forEach{
                dueAmount += (it.totalAmount - it.paidAmount)
            }

            val packetList = databaseManager.getPacketDAO()
                .getPacketList()

            var totalWeight = 0.0
            var totalValue = 0.0

            packetList.forEach{
                totalWeight += it.weight
                totalValue += (it.weight * it.price)
            }

            val currentTime = Calendar.getInstance().timeInMillis

            val overDueDate = databaseManager.getLedgerDAO()
                .getTotalOverDueDate(currentTime)

            val underDueDate = databaseManager.getLedgerDAO()
                .getTotalUnderDueDate(currentTime)

            homeView.onHomeItemsFetchListner(
                totalPayment,
                dueAmount,
                totalWeight,
                totalValue,
                overDueDate,
                underDueDate
            )
        }catch (e: Exception){
            Log.d(TAG, "HomeItemFetchException: ${e.message}")
        }
    }

    override fun fetchOverDueDates() {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!

            val currentTime = Calendar.getInstance().timeInMillis

            val dueDateList = databaseManager.getLedgerDAO()
                .getOverDueDateList(currentTime)

            homeView.onDueDateListFetchLisnter(dueDateList)
        }catch (e: Exception){
            Log.d(TAG, "DueDateException: ${e.message}")
        }
    }

    override fun fetchUnderDueDates() {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!

            val currentTime = Calendar.getInstance().timeInMillis

            val dueDateList = databaseManager.getLedgerDAO()
                .getUnderDueDateList(currentTime)

            homeView.onDueDateListFetchLisnter(dueDateList)
        }catch (e: Exception){
            Log.d(TAG, "DueDateException: ${e.message}")
        }
    }
}