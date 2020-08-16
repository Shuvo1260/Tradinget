package org.binaryitplanet.tradinget.Features.Model

import androidx.room.*
import org.binaryitplanet.tradinget.Utils.BuyUtils
import org.binaryitplanet.tradinget.Utils.BuyerLedgerUtils

@Dao
interface BuyDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(buyUtils: BuyUtils): Long

    @Delete
    fun delete(buyUtils: BuyUtils): Int

    @Update
    fun update(buyUtils: BuyUtils): Int

    @Query("SELECT * FROM Buy_table WHERE Seller_ID ==:sellerId ORDER BY Date_milli ASC")
    fun fetchBuyListBySellerId(sellerId: Long): List<BuyUtils>

    @Query("SELECT * FROM Buy_table WHERE ID ==:id")
    fun fetchBuyById(id: Long): BuyUtils

}