package org.binaryitplanet.tradinget.Features.View.Invoice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import org.binaryitplanet.tradinget.Features.Adapter.InvoiceAdapter
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.databinding.FragmentInvoiceBinding
import java.io.File


class Invoice : Fragment() {

    private val TAG = "Invoice"
    private lateinit var binding: FragmentInvoiceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInvoiceBinding.inflate(inflater, container, false)
//
//        binding.add.setOnClickListener {
//            var intent = Intent(context, CreateInvoice::class.java)
//            startActivity(intent)
//            activity?.overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft)
//        }

        val permissions:Array<String> = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        requestPermissions(permissions, Config.INVOICE_REQUEST_CODE)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        getInvoices()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Log.d(TAG, "code: $requestCode, $permissions, $grantResults")
        if (requestCode == Config.INVOICE_REQUEST_CODE && grantResults.isNotEmpty()) {
            onResume()
        }
    }
    private fun getInvoices() {
        var source = File(Config.PDF_DIR_PATH)

        if (!source.exists())
            source.mkdirs()

        if (source.isDirectory) {
            val invoices = source.list()

            if (!invoices.isNullOrEmpty())
                setupList(invoices)
        }
    }

    private fun setupList(invoices: Array<String>) {
        val adapter = InvoiceAdapter(context!!, invoices)
        binding.list.adapter = adapter
        binding.list.layoutManager = GridLayoutManager(context, 2)
        binding.list.setItemViewCacheSize(Config.LIST_CACHED_SIZE)
    }
}