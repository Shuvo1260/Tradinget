package org.binaryitplanet.tradinget.Features.View.Buyer

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import org.binaryitplanet.tradinget.Features.Common.StakeholderView
import org.binaryitplanet.tradinget.Features.Prsenter.StakeholderPresenterIml
import org.binaryitplanet.tradinget.Features.View.Broker.AddBroker
import org.binaryitplanet.tradinget.Features.View.Ledger.AddLedger
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.StakeholderUtils
import org.binaryitplanet.tradinget.databinding.ActivityViewBrokerBinding
import org.binaryitplanet.tradinget.databinding.ActivityViewBuyerBinding

class ViewBuyer : AppCompatActivity(), StakeholderView {

    private val TAG = "ViewBuyer"
    private lateinit var binding: ActivityViewBuyerBinding
    private lateinit var stakeholder: StakeholderUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_buyer)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.enterTransition = null
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_buyer)

        stakeholder = intent?.getSerializableExtra(Config.STAKEHOLDER) as StakeholderUtils
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
            val intent = Intent(this, AddLedger::class.java)
            intent.putExtra(Config.STAKEHOLDER, stakeholder)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val presenter = StakeholderPresenterIml(this, this)
        presenter.fetchStakeholderById(stakeholder.id!!)
    }

    override fun onFetchStakeholderListener(stakeholder: StakeholderUtils) {
        super.onFetchStakeholderListener(stakeholder)
        this.stakeholder = stakeholder
        setupViews()
    }

    private fun deleteData() {

        val builder = AlertDialog.Builder(this)

        builder.setTitle(Config.DELETE_BUYER_TITLE)
        builder.setMessage(Config.DELETE_BUYER_MESSAGE)

        builder.setIcon(R.drawable.ic_launcher)

        builder.setPositiveButton(Config.YES_MESSAGE){
                dialog: DialogInterface?, which: Int ->
            val presenter = StakeholderPresenterIml(this, this)
            presenter.deleteStakeholder(stakeholder)
        }

        builder.setNegativeButton(
            Config.NO_MESSAGE
        ){
                dialog: DialogInterface?, which: Int ->
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onDeleteStakeholderListener(status: Boolean) {
        super.onDeleteStakeholderListener(status)
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
        val intent = Intent(this, AddBroker::class.java)
        intent.putExtra(Config.OPERATION_FLAG, false)
        intent.putExtra(Config.STAKEHOLDER, stakeholder)
        startActivity(intent)
        overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft)
    }

    private fun setupViews() {
        binding.name.text = stakeholder.name
        binding.mobileNumber.text = stakeholder.mobileNumber
        binding.altMobileNumber.text = stakeholder.altMobileNumber
        binding.firmName.text = stakeholder.firmName
        binding.address.text = stakeholder.address
        binding.gstNumber.text = stakeholder.gstNumber
        binding.panNumber.text = stakeholder.panNumber

        binding.mobileNumber.setOnClickListener {
            makeCall(stakeholder.mobileNumber)
        }
        binding.altMobileNumber.setOnClickListener {
            makeCall(stakeholder.altMobileNumber)
        }
    }

    private fun makeCall(number:String) {
        Log.d(TAG, "Making call to $number")
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$number")
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CALL_PHONE), Config.REQUEST_CALL)
        } else {
            startActivity(intent)
        }
    }


    // Toolbar menu setting
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.view_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setUpToolbar() {

        binding.toolbar.title = stakeholder.name

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