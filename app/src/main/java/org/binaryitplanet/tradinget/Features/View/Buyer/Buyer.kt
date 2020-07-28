package org.binaryitplanet.tradinget.Features.View.Buyer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.binaryitplanet.tradinget.Features.View.Broker.AddBroker
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.databinding.FragmentBuyerBinding

class Buyer : Fragment() {


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

}