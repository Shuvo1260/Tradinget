package org.binaryitplanet.tradinget.Features.View.Inventory

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import org.binaryitplanet.tradinget.Features.Adapter.PacketDetailsAdapter
import org.binaryitplanet.tradinget.Features.Prsenter.PacketDetailsPresenterIml
import org.binaryitplanet.tradinget.Features.Prsenter.PacketPresenterIml
import org.binaryitplanet.tradinget.Features.Prsenter.StakeholderPresenterIml
import org.binaryitplanet.tradinget.Features.View.Broker.AddBroker
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.PacketDetailsUtils
import org.binaryitplanet.tradinget.Utils.PacketUtils
import org.binaryitplanet.tradinget.databinding.ActivityViewBrokerBinding
import org.binaryitplanet.tradinget.databinding.ActivityViewPacketBinding

class ViewPacket : AppCompatActivity(), InventoryView, ViewPacketDetails {

    private val TAG = "ViewPacket"
    private lateinit var binding: ActivityViewPacketBinding

    private lateinit var packet: PacketUtils
    private lateinit var packetDetailsList: ArrayList<PacketDetailsUtils>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_packet)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window?.enterTransition = null
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_packet)

        packet = intent?.getSerializableExtra(Config.PACKET) as PacketUtils

        setUpToolbar()

        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.edit) {
                editData()
            } else if (it.itemId == R.id.delete) {
                deleteData()
            }
            return@setOnMenuItemClickListener super.onOptionsItemSelected(it)
        }

        binding.add.setOnClickListener {
            val intent = Intent(this, AddPacketDetails::class.java)
            intent.putExtra(Config.PACKET, packet)
            intent.putExtra(Config.OPERATION_FLAG, true)
            startActivity(intent)
        }
    }


    override fun onResume() {
        super.onResume()
        val presenter = PacketPresenterIml(this, this)
        presenter.fetchPacketById(packet.id!!)
    }

    override fun onFetchPacketListener(Packet: PacketUtils) {
        super.onFetchPacketListener(Packet)
        packet = Packet
        setupViews()
        setUpRecyclerView()
    }

    override fun onDeleteClickListener(position: Int) {
        super.onDeleteClickListener(position)

        val builder = AlertDialog.Builder(this)

        builder.setTitle(Config.DELETE_PACKET_TITLE)
        builder.setMessage(Config.DELETE_PACKET_MESSAGE)

        builder.setIcon(R.drawable.ic_launcher)

        builder.setPositiveButton(Config.YES_MESSAGE){
                dialog: DialogInterface?, which: Int ->
            val presenter = PacketDetailsPresenterIml(this, this)
            presenter.deletePacketDetails(packetDetailsList[position])
        }

        builder.setNegativeButton(
            Config.NO_MESSAGE
        ){
                dialog: DialogInterface?, which: Int ->
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onDeletePacketDetailsListener(status: Boolean) {
        super.onDeletePacketDetailsListener(status)
        if (status) {
            Toast.makeText(
                this,
                Config.SUCCESS_MESSAGE,
                Toast.LENGTH_SHORT
            ).show()
            setUpRecyclerView()
        } else {
            Toast.makeText(
                this,
                Config.FAILED_MESSAGE,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setUpRecyclerView() {
        val presenter = PacketDetailsPresenterIml(this, this)
        presenter.fetchPacketDetailsList(packet.packetNumber)
    }

    override fun onFetchPacketDetailsListListener(packetDetailsList: List<PacketDetailsUtils>) {
        super.onFetchPacketDetailsListListener(packetDetailsList)
        this.packetDetailsList = packetDetailsList as ArrayList<PacketDetailsUtils>
        val adapter = PacketDetailsAdapter(
            this,
            this,
            packet,
            this.packetDetailsList
        )

        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.setItemViewCacheSize(Config.LIST_CACHED_SIZE)
    }


    private fun deleteData() {

        val builder = AlertDialog.Builder(this)

        builder.setTitle(Config.DELETE_PACKET_TITLE)
        builder.setMessage(Config.DELETE_PACKET_MESSAGE)

        builder.setIcon(R.drawable.ic_launcher)

        builder.setPositiveButton(Config.YES_MESSAGE){
                dialog: DialogInterface?, which: Int ->
            val presenter = PacketPresenterIml(this, this)
            presenter.deletePacket(packet)
        }

        builder.setNegativeButton(
            Config.NO_MESSAGE
        ){
                dialog: DialogInterface?, which: Int ->
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onDeletePacketListener(status: Boolean) {
        super.onDeletePacketListener(status)
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


    private fun editData() {
        val intent = Intent(this, AddPacket::class.java)
        intent.putExtra(Config.OPERATION_FLAG, false)
        intent.putExtra(Config.PACKET, packet)
        startActivity(intent)
        overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft)
    }


    private fun setupViews() {
        binding.packetName.text = packet.packetName
        binding.weight.text = packet.weight.toString() + " " + Config.CTS
        binding.price.text = packet.price.toString() + " " + Config.RUPEE_SIGN
        binding.rate.text = packet.rate.toString()
        binding.code.text = packet.code
        if (packet.remark.isNullOrEmpty())
            binding.remark.visibility = View.GONE
        else
            binding.remark.text = packet.remark
    }


    // Toolbar menu setting
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.view_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setUpToolbar() {

        binding.toolbar.title = packet.packetName

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