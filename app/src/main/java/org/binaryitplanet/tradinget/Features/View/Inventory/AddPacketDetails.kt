package org.binaryitplanet.tradinget.Features.View.Inventory

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import org.binaryitplanet.tradinget.Features.Prsenter.PacketDetailsPresenterIml
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.PacketDetailsUtils
import org.binaryitplanet.tradinget.Utils.PacketUtils
import org.binaryitplanet.tradinget.databinding.ActivityAddPacketBinding
import org.binaryitplanet.tradinget.databinding.ActivityAddPacketDetailsBinding

class AddPacketDetails : AppCompatActivity(), ViewPacketDetails {

    private val TAG = "AddPacketDetails"
    private lateinit var binding: ActivityAddPacketDetailsBinding

    private var isAddOperation: Boolean = true


    private lateinit var packetDetails: PacketDetailsUtils
    private lateinit var packet: PacketUtils

    private lateinit var packetNumber: String
    private lateinit var sieve: String
    private var weight: Double = 0.0
    private var soldWeight: Double = 0.0
    private var remainingWeight: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_packet_details)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_packet_details)

        isAddOperation = intent?.getBooleanExtra(Config.OPERATION_FLAG, true)!!

        setUpToolbar()
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.done) {
                if(checkValidity() && isAddOperation)
                    saveData()
                else if (checkValidity() && !isAddOperation)
                    updateData()
            }
            return@setOnMenuItemClickListener super.onOptionsItemSelected(it)
        }
    }

    override fun onResume() {
        super.onResume()

        packet = intent?.getSerializableExtra(Config.PACKET) as PacketUtils
        if (!isAddOperation) {
            packetDetails = intent?.getSerializableExtra(Config.PACKET_DETAILS) as PacketDetailsUtils
            setViews()
        }
    }

    private fun setViews() {
        binding.packetNumber.setText(packetDetails.packetDetailsNumber)
        binding.sieve.setText(packetDetails.packetDetailsNumber)
        binding.weight.setText(packetDetails.weight.toString())
//        binding.soldWeight.setText(packetDetails.soldWeight.toString())
//        binding.remainingWeight.setText(packetDetails.remainingWeight.toString())

        binding.packetNumber.setSelection(packetDetails.packetDetailsNumber.length)
    }

    private fun updateData() {
        packetDetails.packetDetailsNumber = packetNumber
        packetDetails.sieve = sieve
        packetDetails.weight = weight
//        packetDetails.soldWeight = soldWeight
//        packetDetails.remainingWeight = remainingWeight

        val presenter = PacketDetailsPresenterIml(this, this)
        presenter.updatePacketDetails(packetDetails)
    }

    override fun onUpdatePacketDetailsListener(status: Boolean) {
        super.onUpdatePacketDetailsListener(status)
        if (status) {
            Toast.makeText(
                this,
                Config.SUCCESS_MESSAGE,
                Toast.LENGTH_SHORT
            ).show()
            onBackPressed()
        } else {
            Toast.makeText(
                this,
                Config.FAILED_MESSAGE,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun saveData() {

        packetDetails = PacketDetailsUtils(
            null,
            packet.packetNumber,
            packetNumber,
            sieve,
            weight,
            soldWeight,
            weight
        )

        packet.weight += weight

        val presenter = PacketDetailsPresenterIml(this, this)
        presenter.insertPacketDetails(packet, packetDetails)
    }

    override fun onSavePacketDetailsListener(status: Boolean) {
        super.onSavePacketDetailsListener(status)
        if (status) {
            Toast.makeText(
                this,
                Config.SUCCESS_MESSAGE,
                Toast.LENGTH_SHORT
            ).show()
            onBackPressed()
        } else {
            Toast.makeText(
                this,
                Config.FAILED_MESSAGE,
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun checkValidity(): Boolean {
        packetNumber = binding.packetNumber.text.toString()
        sieve = binding.sieve.text.toString()

        if (packetNumber.isNullOrEmpty()) {
            binding.packetNumber.error = Config.REQUIRED_FIELD
            binding.packetNumber.requestFocus()
            return false
        }
        if (sieve.isNullOrEmpty()) {
            binding.sieve.error = Config.REQUIRED_FIELD
            binding.sieve.requestFocus()
            return false
        }
        if (binding.weight.text.isNullOrEmpty()) {
            binding.weight.error = Config.REQUIRED_FIELD
            binding.weight.requestFocus()
            return false
        }
//        if (binding.soldWeight.text.isNullOrEmpty()) {
//            binding.soldWeight.error = Config.REQUIRED_FIELD
//            binding.soldWeight.requestFocus()
//            return false
//        }
//        if (binding.remainingWeight.text.isNullOrEmpty()) {
//            binding.remainingWeight.error = Config.REQUIRED_FIELD
//            binding.remainingWeight.requestFocus()
//            return false
//        }

        weight = binding.weight.text.toString().toDouble()
//        soldWeight = binding.soldWeight.text.toString().toDouble()
//        remainingWeight = binding.remainingWeight.text.toString().toDouble()

        return true
    }


    // Toolbar menu setting
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.done_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    private fun setUpToolbar() {

        binding.toolbar.title = if (isAddOperation)
            Config.TOOLBAR_TITLE_ADD_PACKET_DETAILS
        else
            Config.TOOLBAR_TITLE_UPDATE_PACKET_DETAILS

        binding.toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.d(TAG, "Back pressed")
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft)
    }
}