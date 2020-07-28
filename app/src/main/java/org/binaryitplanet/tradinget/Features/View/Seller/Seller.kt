package org.binaryitplanet.tradinget.Features.View.Seller

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.binaryitplanet.tradinget.Features.View.Broker.AddBroker
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.databinding.FragmentSellerBinding

class Seller : Fragment() {

    private val TAG = "Seller"
    private lateinit var binding: FragmentSellerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSellerBinding.inflate(inflater, container, false)

        binding.add.setOnClickListener {

            var intent = Intent(context, AddSeller::class.java)
            intent.putExtra(Config.OPERATION_FLAG, true)
            startActivity(intent)
        }
        return binding.root
    }
}