package org.binaryitplanet.tradinget.Features.Model

import androidx.room.*

import org.binaryitplanet.tradinget.Utils.SellerLedgerUtils


@Dao
interface SellerLedgerDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ledger: SellerLedgerUtils): Long

    @Delete
    fun delete(ledger: SellerLedgerUtils): Int

    @Update
    fun update(ledger: SellerLedgerUtils): Int

    @Query("SELECT * FROM SELLER_LEDGER_TABLE WHERE Seller_ID ==:sellerId ORDER BY Date_milli ASC")
    fun fetchLedgerBySellerId(sellerId: Long): List<SellerLedgerUtils>
}