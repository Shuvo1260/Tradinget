package org.binaryitplanet.tradinget.Features.View.Invoice

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.binaryitplanet.tradinget.Features.View.Ledger.AddLedger
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.databinding.FragmentInvoiceBinding
import org.binaryitplanet.tradinget.databinding.FragmentInvoiceSettingsBinding

class InvoiceSettings : Fragment() {


    private val TAG = "InvoiceSettings"
    private lateinit var binding: FragmentInvoiceSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentInvoiceSettingsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        binding.edit.setOnClickListener {
            val intent = Intent(context, InvoiceSettingsEdit::class.java)
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft)
        }
        return binding.root
    }

}