package org.binaryitplanet.tradinget.Utils

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = Config.TABLE_BUY)
data class BuyUtils(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Config.COLUMN_ID)
    val id: Long?,

    @ColumnInfo(name = Config.COLUMN_SELLER_ID)
    val sellerId: Long?,

    @ColumnInfo(name = Config.COLUMN_WEIGHT)
    var weight: Double,

    @ColumnInfo(name = Config.COLUMN_RATE)
    var rate: Double,

    @ColumnInfo(name = Config.COLUMN_DISCOUNT_AMOUNT)
    var discountAmount: Double,

    @ColumnInfo(name = Config.COLUMN_TOTAL_AMOUNT)
    var amount: Double,

    @ColumnInfo(name = Config.COLUMN_DATE)
    var purchaseDate: String,

    @ColumnInfo(name = Config.COLUMN_DATE_MILLI)
    var purchaseDateMilli: Long,

    @ColumnInfo(name = Config.COLUMN_DUE_DATE)
    var dueDate: String,

    @ColumnInfo(name = Config.COLUMN_DUE_DATE_MILLI)
    var dueDateMilli: Long,

    @ColumnInfo(name = Config.COLUMN_BROKER_NAME)
    var brokerName: String?,

    @ColumnInfo(name = Config.COLUMN_BROKER_ID)
    var brokerId: Long,

    @ColumnInfo(name = Config.COLUMN_REMARK)
    var remark: String?
): Serializable {
}