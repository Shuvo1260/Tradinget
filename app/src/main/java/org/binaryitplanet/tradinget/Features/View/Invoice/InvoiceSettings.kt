package org.binaryitplanet.tradinget.Features.View.Invoice

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_create_invoice.*
import org.binaryitplanet.tradinget.Features.Adapter.NoteListAdapter
import org.binaryitplanet.tradinget.Features.Prsenter.InvoicePresenterIml
import org.binaryitplanet.tradinget.Features.View.Ledger.AddLedger
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.InvoiceSettingsUtils
import org.binaryitplanet.tradinget.Utils.NotesUtils
import org.binaryitplanet.tradinget.databinding.FragmentInvoiceBinding
import org.binaryitplanet.tradinget.databinding.FragmentInvoiceSettingsBinding

class InvoiceSettings : Fragment(), InvoiceSettingsView {


    private val TAG = "InvoiceSettings"
    private lateinit var binding: FragmentInvoiceSettingsBinding
    private lateinit var invoiceSettings: InvoiceSettingsUtils
    private var noteList = arrayListOf<NotesUtils>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentInvoiceSettingsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        binding.edit.setOnClickListener {
            val intent = Intent(context, InvoiceSettingsEdit::class.java)
            if (noteList.size == 0){
                intent.putExtra(Config.OPERATION_FLAG, true)
            } else {
                intent.putExtra(Config.OPERATION_FLAG, false)
            }
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val presenter = InvoicePresenterIml(context!!, this)
        presenter.fetchInvoiceSettings()
    }


    override fun onFetchInvoiceSettingsListener(
        invoiceSEttings: InvoiceSettingsUtils,
        noteList: List<NotesUtils>
    ) {
        super.onFetchInvoiceSettingsListener(invoiceSEttings, noteList)
        this.invoiceSettings = invoiceSEttings
        this.noteList = noteList as ArrayList<NotesUtils>

        setupViews()
    }

    private fun setupViews() {
        binding.name.text = "Name: ${invoiceSettings.name}"
        binding.firmName.text = "Firm name: ${invoiceSettings.firmName}"
        binding.mobileNumber.text = "Mobile number: ${invoiceSettings.mobileNumber}"
        binding.altMobileNumber.text = "Alt mobile number: ${invoiceSettings.altMobileNumber}"
        binding.address.text = "Address: ${invoiceSettings.address1},${invoiceSettings.address2}" +
                ",${invoiceSettings.address3},${invoiceSettings.address4}"
        binding.stateCode.text = "State code: ${invoiceSettings.stateCode}"
        binding.gstNumber.text = "GST number: ${invoiceSettings.gstNumber}"
        binding.panNumber.text = "PAN number: ${invoiceSettings.panNumber}"
        binding.bankNameAndBrunch.text = "Bank name and brunch: ${invoiceSettings.bankNameAndBrunch}"
        binding.bankAccount.text = "Bank account: ${invoiceSettings.bankAccount}"
        binding.ifsc.text = "Bank IFSC: ${invoiceSettings.bankIFSC}"
        binding.hsnNumber.text = "HSN number: ${invoiceSettings.hsnNumber}"

        if (invoiceSettings.imageUrl.isNullOrEmpty())
            binding.addImage.visibility = View.GONE
        else
            binding.addImage.setImageURI(Uri.parse(invoiceSettings.imageUrl))

        Log.d(TAG, "Set image: ${invoiceSettings.imageUrl}")

        val adapter = NoteListAdapter(context!!, noteList)
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(context!!)
        binding.list.setItemViewCacheSize(Config.LIST_CACHED_SIZE)
    }

}