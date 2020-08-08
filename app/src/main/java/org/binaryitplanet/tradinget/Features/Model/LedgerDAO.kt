package org.binaryitplanet.tradinget.Features.Model

import androidx.room.*
import org.binaryitplanet.tradinget.Utils.LedgerUtils

@Dao
interface LedgerDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ledgerUtils: LedgerUtils): Long

    @Delete
    fun delete(ledgerUtils: LedgerUtils): Int

    @Update
    fun update(ledgerUtils: LedgerUtils): Int

    @Query("SELECT * FROM LEDGER_TABLE WHERE Stakeholder_ID ==:stakeholderId")
    fun getLedgerListByStakeholderId(stakeholderId: Long): List<LedgerUtils>

    @Query("SELECT * FROM LEDGER_TABLE WHERE Broker_ID ==:brokerId")
    fun getLedgerListByBrokerId(brokerId: Long): List<LedgerUtils>
}