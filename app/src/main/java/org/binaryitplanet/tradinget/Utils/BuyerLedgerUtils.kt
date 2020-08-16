package org.binaryitplanet.tradinget.Utils

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = Config.TABLE_BUYER_LEDGER)
data class BuyerLedgerUtils(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Config.COLUMN_ID)
    val id: Long?,

    @ColumnInfo(name = Config.COLUMN_LEDGER_ID)
    val ledgerId: Long?,

    @ColumnInfo(name = Config.COLUMN_TRANSACTION_TYPE)
    var transactionType: String,

    @ColumnInfo(name = Config.COLUMN_PAYMENT_TYPE)
    var paymentType: String,

    @ColumnInfo(name = Config.COLUMN_TOTAL_AMOUNT)
    var amount: Double,

    @ColumnInfo(name = Config.COLUMN_REMARK)
    var remark: String,

    @ColumnInfo(name = Config.COLUMN_DATE)
    var date: String,

    @ColumnInfo(name = Config.COLUMN_DATE_MILLI)
    var dateMilli: Long,

    @ColumnInfo(name = Config.COLUMN_IS_BROKER)
    var isBroker: Boolean
): Serializable {
}