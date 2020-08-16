package org.binaryitplanet.tradinget.Features.Model

import androidx.room.*
import org.binaryitplanet.tradinget.Utils.BuyerLedgerUtils
import org.binaryitplanet.tradinget.Utils.LedgerUtils

@Dao
interface BuyerLedgerDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ledgerUtils: BuyerLedgerUtils): Long

    @Delete
    fun delete(ledgerUtils: BuyerLedgerUtils): Int

    @Update
    fun update(ledgerUtils: BuyerLedgerUtils): Int

    @Query("SELECT * FROM Buyer_ledger_table WHERE Ledger_ID ==:ledgerId AND Is_broker ==:type ORDER BY Date_milli ASC")
    fun fetchBuyerLedgerByIdAndType(ledgerId: Long, type: Boolean): List<BuyerLedgerUtils>
}