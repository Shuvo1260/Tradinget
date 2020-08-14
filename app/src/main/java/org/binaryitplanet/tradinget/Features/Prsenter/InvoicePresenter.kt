package org.binaryitplanet.tradinget.Features.Prsenter

import org.binaryitplanet.tradinget.Utils.InvoiceSettingsUtils
import org.binaryitplanet.tradinget.Utils.NotesUtils

interface InvoicePresenter {
    fun insertInvoiceSettings(
        invoiceSEttings: InvoiceSettingsUtils,
        noteList: List<NotesUtils>
    )
    fun updateInvoiceSettings(
        invoiceSEttings: InvoiceSettingsUtils,
        noteList: List<NotesUtils>
    )
    fun fetchInvoiceSettings()
}