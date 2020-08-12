package org.binaryitplanet.tradinget.Features.Model

import androidx.room.*
import org.binaryitplanet.tradinget.Utils.PacketDetailsUtils

@Dao
interface PacketDetailsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(packetDetailsUtils: PacketDetailsUtils): Long

    @Delete
    fun delete(packetDetailsUtils: PacketDetailsUtils): Int

    @Update
    fun update(packetDetailsUtils: PacketDetailsUtils): Int

    @Query("SELECT * FROM Packet_details_table WHERE ID ==:id")
    fun getPacketDetailsById(id: Long): PacketDetailsUtils
    
    @Query("SELECT * FROM Packet_details_table WHERE Packet_number ==:packetId ORDER BY Packet_number ASC")
    fun getPacketDetailsList(packetId: String): List<PacketDetailsUtils>

    @Query("SELECT * FROM Packet_details_table ORDER BY Packet_number ASC")
    fun getAllPacketDetailsList(): List<PacketDetailsUtils>
}