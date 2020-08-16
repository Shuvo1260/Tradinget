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
    val weight: Double,

    @ColumnInfo(name = Config.COLUMN_RATE)
    val rate: Double,

    @ColumnInfo(name = Config.COLUMN_DISCOUNT_AMOUNT)
    val discountAmount: Double,

    @ColumnInfo(name = Config.COLUMN_TOTAL_AMOUNT)
    val amount: Double,

    @ColumnInfo(name = Config.COLUMN_DATE)
    val purchaseDate: String,

    @ColumnInfo(name = Config.COLUMN_DATE_MILLI)
    val purchaseDateMilli: Long,

    @ColumnInfo(name = Config.COLUMN_DUE_DATE)
    val dueDate: String,

    @ColumnInfo(name = Config.COLUMN_DUE_DATE_MILLI)
    val dueDateMilli: Long,

    @ColumnInfo(name = Config.COLUMN_BROKER_NAME)
    val brokerName: String?,

    @ColumnInfo(name = Config.COLUMN_BROKER_ID)
    val brokerId: Long,

    @ColumnInfo(name = Config.COLUMN_REMARK)
    val remark: String?
): Serializable {
}