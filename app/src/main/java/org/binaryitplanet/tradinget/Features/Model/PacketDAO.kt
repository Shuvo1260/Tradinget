package org.binaryitplanet.tradinget.Features.Model

import androidx.room.*
import org.binaryitplanet.tradinget.Utils.PacketUtils

@Dao
interface PacketDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(packetUtils: PacketUtils): Long

    @Delete
    fun delete(packetUtils: PacketUtils): Int

    @Update
    fun update(packetUtils: PacketUtils): Int

    @Query("SELECT * FROM Packet_table WHERE ID ==:id")
    fun getPacketById(id: Long): PacketUtils
    
    @Query("SELECT * FROM Packet_table WHERE Weight > 0 ORDER BY Packet_number ASC")
    fun getPacketList(): List<PacketUtils>

    @Query("SELECT * FROM Packet_table WHERE Weight <= 0 ORDER BY Packet_number ASC")
    fun getSoldPacketList(): List<PacketUtils>
}