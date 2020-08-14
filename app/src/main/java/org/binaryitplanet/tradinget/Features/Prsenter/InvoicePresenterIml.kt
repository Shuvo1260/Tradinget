package org.binaryitplanet.tradinget.Features.Prsenter

import android.content.Context
import android.util.Log
import org.binaryitplanet.tradinget.Features.Model.DatabaseManager
import org.binaryitplanet.tradinget.Features.View.Invoice.InvoiceSettingsView
import org.binaryitplanet.tradinget.Utils.InvoiceSettingsUtils
import org.binaryitplanet.tradinget.Utils.NotesUtils
import java.lang.Exception

class InvoicePresenterIml(
    val context: Context,
    val invoiceSettingsView: InvoiceSettingsView
): InvoicePresenter {

    private val TAG = "InvoicePresenter"

    override fun insertInvoiceSettings(
        invoiceSEttings: InvoiceSettingsUtils,
        noteList: List<NotesUtils>
    ) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            databaseManager.getInvoiceSettingsDAO().insert(invoiceSEttings)

            noteList.forEach {
                databaseManager.getNotesDAO().insert(it)
            }

            invoiceSettingsView.onInsertInvoiceSettingsListener(true)

        }catch (e: Exception){
            Log.d(TAG, "InsertSettings: ${e.message}")
            invoiceSettingsView.onInsertInvoiceSettingsListener(false)
        }
    }

    override fun updateInvoiceSettings(
        invoiceSEttings: InvoiceSettingsUtils,
        noteList: List<NotesUtils>
    ) {

        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            databaseManager.getInvoiceSettingsDAO().update(invoiceSEttings)

            noteList.forEach {
                databaseManager.getNotesDAO().insert(it)
            }

            invoiceSettingsView.onUpdateInvoiceSettingsListener(true)

        }catch (e: Exception){
            Log.d(TAG, "InsertSettings: ${e.message}")
            invoiceSettingsView.onUpdateInvoiceSettingsListener(false)
        }
    }

    override fun fetchInvoiceSettings() {

        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val settings = databaseManager.getInvoiceSettingsDAO().fetchInvoiceSettings()

            val notesList = databaseManager.getNotesDAO().fetchNotesList()

            invoiceSettingsView.onFetchInvoiceSettingsListener(settings, notesList)

        }catch (e: Exception){
            Log.d(TAG, "InsertSettings: ${e.message}")
        }
    }
}