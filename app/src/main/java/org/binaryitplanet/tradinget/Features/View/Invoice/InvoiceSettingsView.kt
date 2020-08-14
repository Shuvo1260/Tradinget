package org.binaryitplanet.tradinget.Features.View.Invoice

import org.binaryitplanet.tradinget.Utils.InvoiceSettingsUtils
import org.binaryitplanet.tradinget.Utils.NotesUtils

interface InvoiceSettingsView {
    fun onInsertInvoiceSettingsListener(status: Boolean){}
    fun onUpdateInvoiceSettingsListener(status: Boolean){}
    fun onFetchInvoiceSettingsListener(
        invoiceSEttings: InvoiceSettingsUtils,
        noteList: List<NotesUtils>
    ){}
}