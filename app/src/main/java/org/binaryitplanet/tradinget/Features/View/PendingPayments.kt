package org.binaryitplanet.tradinget.Features.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.binaryitplanet.tradinget.Features.Adapter.PendingLedgerAdapter
import org.binaryitplanet.tradinget.Features.Prsenter.LedgerPresenterIml
import org.binaryitplanet.tradinget.Features.View.Ledger.ViewLedgers
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.LedgerUtils
import org.binaryitplanet.tradinget.databinding.FragmentInvoiceBinding
import org.binaryitplanet.tradinget.databinding.FragmentPendingPaymentsBinding

class PendingPayments : Fragment(), ViewLedgers {


    private val TAG = "PendingPayments"
    private lateinit var binding: FragmentPendingPaymentsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPendingPaymentsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        val presenter = LedgerPresenterIml(context!!, this)
        presenter.fetchPendingLedgerList()
    }

    override fun onFetchLedgerListListener(ledgerList: List<LedgerUtils>) {
        super.onFetchLedgerListListener(ledgerList)
        Log.d(TAG, "List: $ledgerList")

        val adapter = PendingLedgerAdapter(context!!, ledgerList as ArrayList<LedgerUtils>)
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(context!!)
        binding.list.setItemViewCacheSize(Config.LIST_CACHED_SIZE)
    }

}