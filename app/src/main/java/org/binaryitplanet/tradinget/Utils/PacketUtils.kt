package org.binaryitplanet.tradinget.Utils

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = Config.TABLE_PACKET)
data class PacketUtils(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Config.COLUMN_ID)
    var id: Long?,

    @ColumnInfo(name = Config.COLUMN_PACKET_NUMBER)
    var packetNumber: String,

    @ColumnInfo(name = Config.COLUMN_PACKET_NAME)
    var packetName: String,

    @ColumnInfo(name = Config.COLUMN_WEIGHT)
    var weight: Double,

    @ColumnInfo(name = Config.COLUMN_RATE)
    var rate: Double,

    @ColumnInfo(name = Config.COLUMN_PRICE)
    var price: Double,

    @ColumnInfo(name = Config.COLUMN_CODE)
    var code: String,

    @ColumnInfo(name = Config.COLUMN_REMARK)
    var remark: String
): Serializable {
}