package org.binaryitplanet.tradinget.Features.View.Seller

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.databinding.DataBindingUtil
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.databinding.ActivityAddBuyerBinding
import org.binaryitplanet.tradinget.databinding.ActivityAddSellerBinding

class AddSeller : AppCompatActivity() {


    private val TAG = "AddSeller"
    private lateinit var binding: ActivityAddSellerBinding

    private var isAddOperation: Boolean = true

    private lateinit var name: String
    private lateinit var firmName: String
    private lateinit var mobileNumber: String
    private lateinit var altMobileNumber: String
    private lateinit var address: String
    private lateinit var gstNumber: String
    private lateinit var panNumber: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_seller)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_seller)

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


    private fun updateData() {
        Log.d(TAG, "Updating...")
    }

    private fun saveData() {
        Log.d(TAG, "Saving...")
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
        if (firmName.isNullOrEmpty()) {
            binding.firmName.error = Config.REQUIRED_FIELD
            binding.firmName.requestFocus()
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
        if (address.isNullOrEmpty()) {
            binding.address.error = Config.REQUIRED_FIELD
            binding.address.requestFocus()
            return false
        }
        if (gstNumber.isNullOrEmpty()) {
            binding.gstNumber.error = Config.REQUIRED_FIELD
            binding.gstNumber.requestFocus()
            return false
        }
        if (panNumber.isNullOrEmpty()) {
            binding.panNumber.error = Config.REQUIRED_FIELD
            binding.panNumber.requestFocus()
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
            Config.TOOLBAR_TITLE_ADD_BUYER
        else
            Config.TOOLBAR_TITLE_UPDATE_BUYER

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