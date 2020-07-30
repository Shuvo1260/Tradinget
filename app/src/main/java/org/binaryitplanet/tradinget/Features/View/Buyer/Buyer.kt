package org.binaryitplanet.tradinget.Features.View.Buyer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.binaryitplanet.tradinget.Features.Adapter.StakeholderAdapter
import org.binaryitplanet.tradinget.Features.Common.StakeholderView
import org.binaryitplanet.tradinget.Features.Prsenter.StakeholderPresenterIml
import org.binaryitplanet.tradinget.Features.View.Broker.AddBroker
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.StakeholderUtils
import org.binaryitplanet.tradinget.databinding.FragmentBuyerBinding

class Buyer : Fragment(), StakeholderView {


    private val TAG = "Buyer"
    private lateinit var binding: FragmentBuyerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBuyerBinding.inflate(inflater, container, false)

        binding.add.setOnClickListener {

            var intent = Intent(context, AddBuyer::class.java)
            intent.putExtra(Config.OPERATION_FLAG, true)
            startActivity(intent)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val presenter = StakeholderPresenterIml(context!!, this)

        presenter.fetchStakeholder(Config.TYPE_ID_BUYER)
    }

    override fun onFetchStakeholderListListener(stakeholderList: List<StakeholderUtils>) {
        super.onFetchStakeholderListListener(stakeholderList)
        var adapter = StakeholderAdapter(
            context!!,
            stakeholderList as ArrayList<StakeholderUtils>
        )

        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.setItemViewCacheSize(Config.LIST_CACHED_SIZE)
    }

}