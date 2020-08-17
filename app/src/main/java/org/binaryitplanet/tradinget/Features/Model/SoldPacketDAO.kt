package org.binaryitplanet.tradinget.Features.Model

import androidx.room.*
import org.binaryitplanet.tradinget.Utils.SoldPacketUtils

@Dao
interface SoldPacketDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(soldPacketUtils: SoldPacketUtils): Long

    @Delete
    fun delete(soldPacketUtils: SoldPacketUtils): Int

    @Update
    fun update(soldPacketUtils: SoldPacketUtils): Int

    @Query("SELECT * FROM Sold_packet_table WHERE Ledger_ID ==:ledgerId")
    fun getSoldPacketListByLedgerId(ledgerId: String): List<SoldPacketUtils>

    @Query("SELECT * FROM Sold_packet_table")
    fun getSoldPacketList(): List<SoldPacketUtils>

    @Query("DELETE FROM Sold_packet_table WHERE Ledger_ID ==:ledgerId")
    fun deleteAllSoldPacketsByLedgerId(ledgerId: String)
}