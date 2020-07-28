package org.binaryitplanet.tradinget.Features.View.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.databinding.FragmentHomeBinding

class Home : Fragment() {

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