package org.binaryitplanet.tradinget.Features.View.Broker

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import org.binaryitplanet.tradinget.Features.Common.StakeholderView
import org.binaryitplanet.tradinget.Features.Prsenter.StakeholderPresenterIml
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.StakeholderUtils
import org.binaryitplanet.tradinget.databinding.ActivityAddBrokerBinding
import org.binaryitplanet.tradinget.databinding.ActivityAddBuyerBinding

class AddBroker : AppCompatActivity(), StakeholderView {


    private val TAG = "AddBroker"
    private lateinit var binding: ActivityAddBrokerBinding

    private var isAddOperation: Boolean = true

    private lateinit var name: String
    private lateinit var firmName: String
    private lateinit var mobileNumber: String
    private lateinit var altMobileNumber: String
    private lateinit var address: String
    private lateinit var gstNumber: String
    private lateinit var panNumber: String

    private lateinit var stakeholder: StakeholderUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_broker)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_broker)

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
            stakeholder = intent?.getSerializableExtra(Config.STAKEHOLDER) as StakeholderUtils
            setViews()
        }
    }



    // Updating data portion starts
    private fun updateData() {
        Log.d(TAG, "Updating...")

        stakeholder.name = name
        stakeholder.firmName = firmName
        stakeholder.mobileNumber = mobileNumber
        stakeholder.address = address
        stakeholder.gstNumber = gstNumber
        stakeholder.panNumber = panNumber

        val presenter = StakeholderPresenterIml(this, this)
        presenter.updateStakeholder(stakeholder)
    }

    override fun onUpdateStakeholderListener(status: Boolean) {
        super.onUpdateStakeholderListener(status)
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
    // Updating data portion ends

    // Saving data portion starts
    private fun saveData() {
        Log.d(TAG, "Saving...")
        stakeholder = StakeholderUtils(
            null,
            Config.TYPE_ID_BROKER,
            name,
            firmName,
            mobileNumber,
            altMobileNumber,
            address,
            gstNumber,
            panNumber
        )

        val presenter = StakeholderPresenterIml(this, this)
        presenter.insertStakeholder(stakeholder)
    }

    override fun onSaveStakeholderListener(status: Boolean) {
        super.onSaveStakeholderListener(status)
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
    // Saving data portion ends


    private fun setViews() {
        binding.name.setText(stakeholder.name)
        binding.firmName.setText(stakeholder.firmName)
        binding.mobileNumber.setText(stakeholder.mobileNumber)
        binding.altMobileNumber.setText(stakeholder.altMobileNumber)
        binding.address.setText(stakeholder.address)
        binding.gstNumber.setText(stakeholder.gstNumber)
        binding.panNumber.setText(stakeholder.panNumber)

        binding.name.setSelection(stakeholder.name.length)
    }


    private fun checkValidity(): Boolean {
        name = binding.name.text.toString()
        firmName = binding.firmName.text.toString()
        mobileNumber = binding.mobileNumber.text.toString()
        altMobileNumber = binding.altMobileNumber.text.toString()
        address = binding.address.text.toString()
        gstNumber = binding.gstNumber.text.toString()
        panNumber = binding.panNumber.text.toString()

        if (name.isNullOrEmpty()) {
            binding.name.error = Config.REQUIRED_FIELD
            binding.name.requestFocus()
            return false
        }
        if (mobileNumber.isNullOrEmpty()) {
            binding.mobileNumber.error = Config.REQUIRED_FIELD
            binding.mobileNumber.requestFocus()
            return false
        }
        if (altMobileNumber.isNullOrEmpty()) {
            binding.altMobileNumber.error = Config.REQUIRED_FIELD
            binding.altMobileNumber.requestFocus()
            return false
        }

        return true
    }

    // Toolbar menu setting
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.done_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setUpToolbar() {

        binding.toolbar.title = if (isAddOperation)
            Config.TOOLBAR_TITLE_ADD_BROKER
        else
            Config.TOOLBAR_TITLE_UPDATE_BROKER

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