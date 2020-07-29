package org.binaryitplanet.tradinget.Features.View.Inventory

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import org.binaryitplanet.tradinget.Features.Adapter.ViewPagerAdapter
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.databinding.FragmentInventoryBinding

class Inventory : Fragment() {

    private val TAG = "Inventory"
    private lateinit var binding: FragmentInventoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentInventoryBinding.inflate(
            inflater,
            container,
            false
        )

        setupViewPager()

        return binding.root
    }

    private fun setupViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        viewPagerAdapter.addFragment(Packets())
        viewPagerAdapter.addFragment(SoldPackets())

        binding.viewPager.adapter = viewPagerAdapter

        binding.viewPager.currentItem = Config.PACKETS_CODE
        binding.packets.setTextColor(Color.WHITE)
        binding.soldPackets.setTextColor(Color.GRAY)

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
                //
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                //
            }

            override fun onPageSelected(position: Int) {
                if (position == Config.PACKETS_CODE) {
                    binding.packets.setTextColor(Color.WHITE)
                    binding.soldPackets.setTextColor(Color.GRAY)
                } else if(position == Config.SOLD_PACKETS_CODE) {
                    binding.packets.setTextColor(Color.GRAY)
                    binding.soldPackets.setTextColor(Color.WHITE)
                }
            }

        })

        binding.packets.setOnClickListener {
            binding.viewPager.currentItem = Config.PACKETS_CODE
        }
        binding.soldPackets.setOnClickListener {
            binding.viewPager.currentItem = Config.SOLD_PACKETS_CODE
        }
    }
}