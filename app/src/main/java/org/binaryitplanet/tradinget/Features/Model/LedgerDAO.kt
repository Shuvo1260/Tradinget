package org.binaryitplanet.tradinget.Features.Model

import androidx.room.*
import org.binaryitplanet.tradinget.Utils.LedgerUtils
import java.time.Month

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

    @Query("SELECT * FROM LEDGER_TABLE WHERE Ledger_ID ==:id")
    fun getLedgerByLedgerId(id: String): LedgerUtils

    @Query("SELECT SUM(Total_amount) FROM LEDGER_TABLE WHERE Month ==:month")
    fun getTotalPaymentOfMonth(month: Int): Double

    @Query("SELECT * FROM LEDGER_TABLE WHERE Paid_amount < Total_amount")
    fun getDueLedgerList(): List<LedgerUtils>

    @Query("SELECT COUNT(Due_date_milli) FROM LEDGER_TABLE WHERE Due_date_milli <= :currentTime AND Paid_amount < Total_amount")
    fun getTotalOverDueDate(currentTime: Long): Int

    @Query("SELECT COUNT(Due_date_milli) FROM LEDGER_TABLE WHERE Due_date_milli > :currentTime AND Paid_amount < Total_amount")
    fun getTotalUnderDueDate(currentTime: Long): Int

    @Query("SELECT * FROM LEDGER_TABLE WHERE Due_date_milli <= :currentTime AND Paid_amount < Total_amount")
    fun getOverDueDateList(currentTime: Long): List<LedgerUtils>

    @Query("SELECT * FROM LEDGER_TABLE WHERE Due_date_milli > :currentTime AND Paid_amount < Total_amount")
    fun getUnderDueDateList(currentTime: Long): List<LedgerUtils>

    @Query("SELECT Ledger_ID FROM LEDGER_TABLE")
    fun getLedgerIDList(): List<String>


    @Query("SELECT * FROM LEDGER_TABLE WHERE Paid_amount < Total_amount ORDER BY Stakeholder_name ASC")
    fun getPendingLedgerList(): List<LedgerUtils>
}