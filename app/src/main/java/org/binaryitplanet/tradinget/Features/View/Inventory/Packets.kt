package org.binaryitplanet.tradinget.Features.View.Inventory

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.databinding.FragmentPacketsBinding

class Packets : Fragment() {

    private val TAG = "Inventory"
    private lateinit var binding: FragmentPacketsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPacketsBinding.inflate(
            inflater,
            container,
            false
        )

        binding.add.setOnClickListener {
            var intent = Intent(context, AddPacket::class.java)
            intent.putExtra(Config.OPERATION_FLAG, true)
            startActivity(intent)
        }

        return binding.root
    }

}