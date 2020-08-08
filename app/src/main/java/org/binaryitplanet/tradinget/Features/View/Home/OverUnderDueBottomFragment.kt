package org.binaryitplanet.tradinget.Features.View.Home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.binaryitplanet.tradinget.Features.Adapter.DueDateAdapter
import org.binaryitplanet.tradinget.Features.Prsenter.HomePresenterIml
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.LedgerUtils
import org.binaryitplanet.tradinget.databinding.FragmentHomeBinding
import org.binaryitplanet.tradinget.databinding.FragmentOverUnderDueBottomBinding

class OverUnderDueBottomFragment(
    val isOverDue: Boolean
) : BottomSheetDialogFragment(), HomeView {


    private val TAG = "DueBottomFragment"
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

        val presenter = HomePresenterIml(context!!, this)

        if (isOverDue) {
            presenter.fetchOverDueDates()
        } else {
            presenter.fetchUnderDueDates()
        }
    }

    override fun onDueDateListFetchLisnter(dueDateList: List<LedgerUtils>) {
        super.onDueDateListFetchLisnter(dueDateList)
        Log.d(TAG, "DueDates: $dueDateList")

        val adapter = DueDateAdapter(context!!, dueDateList as ArrayList<LedgerUtils>)
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.setItemViewCacheSize(Config.LIST_CACHED_SIZE)
    }
}