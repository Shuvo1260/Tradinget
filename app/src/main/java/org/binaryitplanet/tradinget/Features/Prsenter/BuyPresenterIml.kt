package org.binaryitplanet.tradinget.Features.Prsenter

import android.content.Context
import android.util.Log
import org.binaryitplanet.tradinget.Features.Model.DatabaseManager
import org.binaryitplanet.tradinget.Features.View.Seller.BuyView
import org.binaryitplanet.tradinget.Utils.BuyUtils
import java.lang.Exception

class BuyPresenterIml(
    val context: Context,
    val buyView: BuyView
): BuyPresenter {
    private val TAG = "BuyPresenter"
    override fun insertBuy(buyUtils: BuyUtils) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            databaseManager.getBuyDAO().insert(buyUtils)
            buyView.insertBuyListener(true)
        } catch (e: Exception){
            Log.d(TAG, "BuyInsertError: ${e.message}")
            buyView.insertBuyListener(false)
        }
    }

    override fun deleteBuy(buyUtils: BuyUtils) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val id = databaseManager.getBuyDAO().delete(buyUtils)
            if (id > 0) {
                databaseManager.getSellerLedgerDAO()
                    .deleteLedgersByBuyId(buyUtils.id!!)
            }
            buyView.deleteBuyListener(true)
        } catch (e: Exception){
            Log.d(TAG, "BuyUpdateError: ${e.message}")
            buyView.deleteBuyListener(false)
        }
    }

    override fun updateBuy(buyUtils: BuyUtils) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            databaseManager.getBuyDAO().update(buyUtils)
            buyView.updateBuyListener(true)
        } catch (e: Exception){
            Log.d(TAG, "BuyUpdateError: ${e.message}")
            buyView.updateBuyListener(false)
        }
    }

    override fun fetchBuyListBySellerId(sellerId: Long) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            var buyList = databaseManager.getBuyDAO().fetchBuyListBySellerId(sellerId)
            buyView.fetchBuyListListener(buyList)
        } catch (e: Exception){
            Log.d(TAG, "BuyInsertError: ${e.message}")
        }
    }

    override fun fetchBuyById(id: Long) {

        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            var buy = databaseManager.getBuyDAO().fetchBuyById(id)
            buyView.fetchBuyListener(buy)
        } catch (e: Exception){
            Log.d(TAG, "BuyInsertError: ${e.message}")
        }
    }


}