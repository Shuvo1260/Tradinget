package org.binaryitplanet.tradinget.Utils

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = Config.TABLE_INVOICE_SETTINGS)
data class InvoiceSettingsUtils(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Config.COLUMN_ID)
    val id: Long?,

    @ColumnInfo(name = Config.COLUMN_NAME)
    var name: String,

    @ColumnInfo(name = Config.COLUMN_FIRM_NAME)
    var firmName: String?,

    @ColumnInfo(name = Config.COLUMN_MOBILE_NUMBER)
    var mobileNumber: String,

    @ColumnInfo(name = Config.COLUMN_ALT_MOBILE_NUMBER)
    var altMobileNumber: String,

    @ColumnInfo(name = Config.COLUMN_ADDRESS1)
    var address1: String?,

    @ColumnInfo(name = Config.COLUMN_ADDRESS2)
    var address2: String?,

    @ColumnInfo(name = Config.COLUMN_ADDRESS3)
    var address3: String?,

    @ColumnInfo(name = Config.COLUMN_ADDRESS4)
    var address4: String?,

    @ColumnInfo(name = Config.COLUMN_STATE_CODE)
    var stateCode: String?,

    @ColumnInfo(name = Config.COLUMN_GST_NUMBER)
    var gstNumber: String?,

    @ColumnInfo(name = Config.COLUMN_PAN_NUMBER)
    var panNumber: String?,

    @ColumnInfo(name = Config.COLUMN_BANK_NAME)
    var bankNameAndBrunch: String?,

    @ColumnInfo(name = Config.COLUMN_BANK_ACCOUNT)
    var bankAccount: String?,

    @ColumnInfo(name = Config.COLUMN_BANK_IFSC)
    var bankIFSC: String?
): Serializable {
}