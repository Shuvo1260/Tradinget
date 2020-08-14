package org.binaryitplanet.tradinget.Features.Model

import androidx.room.*
import org.binaryitplanet.tradinget.Utils.InvoiceSettingsUtils
import org.binaryitplanet.tradinget.Utils.LedgerUtils

@Dao
interface InvoiceSettingsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(invoiceSettings: InvoiceSettingsUtils): Long

    @Delete
    fun delete(invoiceSettings: InvoiceSettingsUtils): Int

    @Update
    fun update(invoiceSettings: InvoiceSettingsUtils): Int

    @Query("SELECT * FROM INVOICE_SETTINGS_TABLE")
    fun fetchInvoiceSettings(): InvoiceSettingsUtils
}