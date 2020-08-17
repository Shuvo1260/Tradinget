package org.binaryitplanet.tradinget.Utils

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = Config.TABLE_SOLD_PACKET)
data class SoldPacketUtils(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Config.COLUMN_ID)
    var id: Long?,

    @ColumnInfo(name = Config.COLUMN_LEDGER_ID)
    var ledgerId: String?,

    @ColumnInfo(name = Config.COLUMN_STAKEHOLDER_NAME)
    var buyerName: String?,

    @ColumnInfo(name = Config.COLUMN_DATE)
    var date: String?,

    @ColumnInfo(name = Config.COLUMN_DATE_MILLI)
    var dateInMilli: Long,

    @ColumnInfo(name = Config.COLUMN_PACKET_NUMBER)
    var packetNumber: String,

    @ColumnInfo(name = Config.COLUMN_PACKET_NAME)
    var packetName: String,

    @ColumnInfo(name = Config.COLUMN_PACKET_DETAILS_NUMBER)
    var packetDetailsNumber: String,

    @ColumnInfo(name = Config.COLUMN_SIEVE)
    var sieve: String,

    @ColumnInfo(name = Config.COLUMN_WEIGHT)
    var weight: Double,

    @ColumnInfo(name = Config.COLUMN_RATE)
    var rate: Double,

    @ColumnInfo(name = Config.COLUMN_CODE)
    var code: String,

    var packetIndex: Int,

    var subPacketIndex: Int
): Serializable {
}