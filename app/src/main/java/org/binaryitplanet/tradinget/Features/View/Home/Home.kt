package org.binaryitplanet.tradinget.Features.View.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.binaryitplanet.tradinget.Features.Prsenter.HomePresenterIml
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.databinding.FragmentHomeBinding

class Home : Fragment(), HomeView {

    private val TAG = "Home"
    private lateinit var binding: FragmentHomeBinding

            override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupListeners()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val presenter = HomePresenterIml(context!!, this)
        presenter.fetchHomeItems()
    }

    override fun onHomeItemsFetchListner(
        totalPayment: Double,
        duePayment: Double,
        weight: Double,
        inventoryValue: Double,
        overDueDate: Int,
        underDueDate: Int,
        totalSellerAmount: Double,
        sellerDueAmount: Double
    ) {
        super.onHomeItemsFetchListner(
            totalPayment,
            duePayment,
            weight,
            inventoryValue,
            overDueDate,
            underDueDate,
            totalSellerAmount,
            sellerDueAmount
        )
        binding.paymentOfThisMonth.text = Config.RUPEE_SIGN + " " + totalPayment
        binding.duePayment.text = Config.RUPEE_SIGN + " " + duePayment
        binding.totalInventoryWeight.text = weight.toString() + " " + Config.CTS
        binding.totalInventoryValue.text = Config.RUPEE_SIGN + " " + inventoryValue
        binding.totalSellerAmount.text = Config.RUPEE_SIGN + " " + totalSellerAmount
        binding.sellerDueAmount.text = Config.RUPEE_SIGN + " " + sellerDueAmount

        binding.overDueDate.text = overDueDate.toString()
        binding.underDueDate.text = underDueDate.toString()
    }

    private fun setupListeners() {
        binding.overDueDate.setOnClickListener {
            var bottomFragment = OverUnderDueBottomFragment(true)
            bottomFragment.show(childFragmentManager, Config.OVER_DUE_DATE)
        }
        binding.underDueDate.setOnClickListener {
            var bottomFragment = OverUnderDueBottomFragment(false)
            bottomFragment.show(childFragmentManager, Config.UNDER_DUE_DATE)
        }
    }
}