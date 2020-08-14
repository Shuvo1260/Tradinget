package org.binaryitplanet.tradinget.Utils

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = Config.TABLE_STAKEHOLDER)
data class StakeholderUtils(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Config.COLUMN_ID)
    val id: Long?,

    @ColumnInfo(name = Config.COLUMN_TYPE)
    val type: Int,

    @ColumnInfo(name = Config.COLUMN_NAME)
    var name: String,

    @ColumnInfo(name = Config.COLUMN_FIRM_NAME)
    var firmName: String?,

    @ColumnInfo(name = Config.COLUMN_MOBILE_NUMBER)
    var mobileNumber: String,

    @ColumnInfo(name = Config.COLUMN_ALT_MOBILE_NUMBER)
    var altMobileNumber: String,

    @ColumnInfo(name = Config.COLUMN_ADDRESS)
    var address: String?,

    @ColumnInfo(name = Config.COLUMN_STATE_CODE)
    var stateCode: String?,

    @ColumnInfo(name = Config.COLUMN_GST_NUMBER)
    var gstNumber: String?,

    @ColumnInfo(name = Config.COLUMN_PAN_NUMBER)
    var panNumber: String?
): Serializable {
}