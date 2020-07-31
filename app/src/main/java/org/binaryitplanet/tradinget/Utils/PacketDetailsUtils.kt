package org.binaryitplanet.tradinget.Utils

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = Config.TABLE_PACKET_DETAILS)
data class PacketDetailsUtils(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Config.COLUMN_ID)
    var id: Long?,

    @ColumnInfo(name = Config.COLUMN_PACKET_NUMBER)
    var packetNumber: String,

    @ColumnInfo(name = Config.COLUMN_PACKET_DETAILS_NUMBER)
    var packetDetailsNumber: String,

    @ColumnInfo(name = Config.COLUMN_SIEVE)
    var sieve: String,

    @ColumnInfo(name = Config.COLUMN_WEIGHT)
    var weight: Double,

    @ColumnInfo(name = Config.COLUMN_SOLD_WEIGHT)
    var soldWeight: Double,

    @ColumnInfo(name = Config.COLUMN_REMAINING_WEIGHT)
    var remainingWeight: Double
): Serializable {
}