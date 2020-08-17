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

    @Query("SELECT * FROM SELLER_LEDGER_TABLE WHERE Ledger_ID ==:id ORDER BY Date_milli ASC")
    fun fetchLedgerById(id: Long): List<SellerLedgerUtils>

    @Query("SELECT SUM(Total_amount) FROM SELLER_LEDGER_TABLE WHERE Transaction_type ==:type")
    fun fetchTotalCreditDebit(type: String): Double

    @Query("DELETE FROM SELLER_LEDGER_TABLE WHERE Ledger_ID ==:id")
    fun deleteLedgersByBuyId(id: Long)
}