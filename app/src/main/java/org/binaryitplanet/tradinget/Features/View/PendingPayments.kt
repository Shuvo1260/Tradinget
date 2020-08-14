package org.binaryitplanet.tradinget.Features.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.databinding.FragmentInvoiceBinding
import org.binaryitplanet.tradinget.databinding.FragmentPendingPaymentsBinding

class PendingPayments : Fragment() {


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

}