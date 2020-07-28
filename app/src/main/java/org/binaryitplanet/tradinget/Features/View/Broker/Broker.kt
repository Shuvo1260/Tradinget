package org.binaryitplanet.tradinget.Features.View.Broker

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.databinding.FragmentBrokerBinding

class Broker : Fragment() {

    private val TAG = "Broker"
    private lateinit var binding: FragmentBrokerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBrokerBinding.inflate(inflater, container, false)

        binding.add.setOnClickListener {
            var intent = Intent(context, AddBroker::class.java)
            intent.putExtra(Config.OPERATION_FLAG, true)
            startActivity(intent)
        }

        return binding.root
    }

}