package org.binaryitplanet.tradinget.Utils

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = Config.TABLE_LEDGER)
data class LedgerUtils(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Config.COLUMN_ID)
    var id: Long?,

    @ColumnInfo(name = Config.COLUMN_LEDGER_ID)
    var ledgerId: String?,

    @ColumnInfo(name = Config.COLUMN_STAKEHOLDER_ID)
    var stakeHolderId: Long,

    @ColumnInfo(name = Config.COLUMN_STAKEHOLDER_NAME)
    var stakeHolderName: String,

    @ColumnInfo(name = Config.COLUMN_MOBILE_NUMBER)
    var mobileNumber: String,

    @ColumnInfo(name = Config.COLUMN_BROKER_ID)
    var brokerId: Long?,

    @ColumnInfo(name = Config.COLUMN_BROKER_NAME)
    var brokerName: String?,

    @ColumnInfo(name = Config.COLUMN_BROKER_PERCENTAGE)
    var brokerPercentage: Double,

    @ColumnInfo(name = Config.COLUMN_BROKER_AMOUNT)
    var brokerAmount: Double,

    @ColumnInfo(name = Config.COLUMN_BROKER_AMOUNT_PAID)
    var brokerAmountPaid: Double,

    @ColumnInfo(name = Config.COLUMN_BROKER_AMOUNT_REMAINING)
    var brokerAmountRemaining: Double,

    @ColumnInfo(name = Config.COLUMN_BROKERAGE_AMOUNT)
    var brokerageAmount: Double,

    @ColumnInfo(name = Config.COLUMN_DISCOUNT_PERCENTAGE)
    var discountPercentage: Double,

    @ColumnInfo(name = Config.COLUMN_DISCOUNT_AMOUNT)
    var discountAmount: Double,

    @ColumnInfo(name = Config.COLUMN_TOTAL_WEIGHT)
    var totalWeight: Double,

    @ColumnInfo(name = Config.COLUMN_TOTAL_AMOUNT)
    var totalAmount: Double,

    @ColumnInfo(name = Config.COLUMN_PAID_AMOUNT)
    var paidAmount: Double,

    @ColumnInfo(name = Config.COLUMN_TOTAL_PACKETS)
    var totalPackets: Int,

    @ColumnInfo(name = Config.COLUMN_PACKET_NAME)
    var firstPacketName: String,

    @ColumnInfo(name = Config.COLUMN_MONTH)
    var month: Int,

    @ColumnInfo(name = Config.COLUMN_DATE)
    var date: String,

    @ColumnInfo(name = Config.COLUMN_DATE_MILLI)
    var dateInMilli: Long,

    @ColumnInfo(name = Config.COLUMN_DUE_DATE)
    var dueDate: String,

    @ColumnInfo(name = Config.COLUMN_DUE_DATE_MILLI)
    var dueDateInMilli: Long,

    @ColumnInfo(name = Config.COLUMN_PAYMENT_TYPE)
    var paymentType: String?,

    @ColumnInfo(name = Config.COLUMN_REMARK)
    var remark: String?,

    @ColumnInfo(name = Config.COLUMN_IMAGE_URL)
    var imageUrl: String?,

    @ColumnInfo(name = Config.COLUMN_INVOICE_URL)
    var invoicePath: String?
): Serializable{
}