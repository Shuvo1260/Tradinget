package org.binaryitplanet.tradinget.Features.View.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.databinding.FragmentHomeBinding
import org.binaryitplanet.tradinget.databinding.FragmentOverUnderDueBottomBinding

class OverUnderDueBottomFragment(
    val isOverDue: Boolean
) : BottomSheetDialogFragment() {


    private val TAG = "OverUnderDueBottomFragment"
    private lateinit var binding: FragmentOverUnderDueBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentOverUnderDueBottomBinding.inflate(
            inflater,
            container,
            false
        )


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.name.text = if (isOverDue)
            Config.OVER_DUE_DATE
        else
            Config.UNDER_DUE_DATE
    }
}