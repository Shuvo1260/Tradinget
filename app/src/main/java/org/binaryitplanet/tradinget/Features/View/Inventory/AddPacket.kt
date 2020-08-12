package org.binaryitplanet.tradinget.Features.View.Inventory

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import org.binaryitplanet.tradinget.Features.Prsenter.PacketPresenterIml
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.PacketUtils
import org.binaryitplanet.tradinget.databinding.ActivityAddPacketBinding

class AddPacket : AppCompatActivity(), InventoryView {


    private val TAG = "AddPacket"
    private lateinit var binding: ActivityAddPacketBinding

    private var isAddOperation: Boolean = true

    private lateinit var packet: PacketUtils

    private lateinit var packetNumber: String
    private lateinit var packetName: String
    private var weight: Double = 0.0
    private var rate: Double = 0.0
    private var price: Double = 0.0
    private lateinit var code: String
    private lateinit var remark: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_packet)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_packet)

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
        if (!isAddOperation) {
            packet = intent?.getSerializableExtra(Config.PACKET) as PacketUtils
            setViews()
        }
    }

    private fun setViews() {
        binding.packetNumber.setText(packet.packetNumber)
        binding.packetName.setText(packet.packetName)
        binding.weight.setText(packet.weight.toString())
        binding.rate.setText(packet.rate.toString())
//        binding.price.setText(packet.price.toString())
        binding.code.setText(packet.code)
        binding.remark.setText(packet.remark)

        binding.packetNumber.setSelection(packet.packetNumber.length)
    }


    private fun updateData() {
        Log.d(TAG, "Updating...")

        packet.packetNumber = packetNumber
        packet.packetName = packetName
        packet.weight = weight
        packet.rate = rate
        packet.price = price
        packet.code = code
        packet.remark = remark

        val presenter = PacketPresenterIml(this, this)
        presenter.updatePacket(packet)
    }

    override fun onUpdatePacketListener(status: Boolean) {
        super.onUpdatePacketListener(status)
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
        Log.d(TAG, "Saving...")
        packet = PacketUtils(
            null,
            packetNumber,
            packetName,
            weight,
            rate,
            price,
            code,
            remark
        )
        val presenter = PacketPresenterIml(this, this)
        presenter.insertPacket(packet)
    }

    override fun onSavePacketListener(status: Boolean) {
        super.onSavePacketListener(status)

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
        packetName = binding.packetName.text.toString()
        code = binding.code.text.toString()
        remark = binding.remark.text.toString()

        if (packetNumber.isNullOrEmpty()) {
            binding.packetNumber.error = Config.REQUIRED_FIELD
            binding.packetNumber.requestFocus()
            return false
        }
        if (packetName.isNullOrEmpty()) {
            binding.packetName.error = Config.REQUIRED_FIELD
            binding.packetName.requestFocus()
            return false
        }
        if (binding.weight.text.isNullOrEmpty()) {
            binding.weight.error = Config.REQUIRED_FIELD
            binding.weight.requestFocus()
            return false
        }
        if (binding.rate.text.isNullOrEmpty()) {
            binding.rate.error = Config.REQUIRED_FIELD
            binding.rate.requestFocus()
            return false
        }
//        if (binding.price.text.isNullOrEmpty()) {
//            binding.price.error = Config.REQUIRED_FIELD
//            binding.price.requestFocus()
//            return false
//        }
        if (code.isNullOrEmpty()) {
            binding.code.error = Config.REQUIRED_FIELD
            binding.code.requestFocus()
            return false
        }
//        if (remark.isNullOrEmpty()) {
//            binding.remark.error = Config.REQUIRED_FIELD
//            binding.remark.requestFocus()
//            return false
//        }

        weight = binding.weight.text.toString().toDouble()
        rate = binding.rate.text.toString().toDouble()
        price = weight * rate

        return true
    }


    // Toolbar menu setting
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.done_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    private fun setUpToolbar() {

        binding.toolbar.title = if (isAddOperation)
            Config.TOOLBAR_TITLE_ADD_PACKET
        else
            Config.TOOLBAR_TITLE_UPDATE_PACKET

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