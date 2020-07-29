package org.binaryitplanet.tradinget.Features.View.Inventory

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.databinding.DataBindingUtil
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.databinding.ActivityAddBrokerBinding
import org.binaryitplanet.tradinget.databinding.ActivityAddPacketBinding

class AddPacket : AppCompatActivity() {


    private val TAG = "AddBroker"
    private lateinit var binding: ActivityAddPacketBinding

    private var isAddOperation: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_packet)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_packet)

        isAddOperation = intent?.getBooleanExtra(Config.OPERATION_FLAG, true)!!

        setUpToolbar()
        binding.toolbar.setOnMenuItemClickListener {
//            if (it.itemId == R.id.done) {
//                if(checkValidity() && isAddOperation)
//                    saveData()
//                else if (checkValidity() && !isAddOperation)
//                    updateData()
//            }
            return@setOnMenuItemClickListener super.onOptionsItemSelected(it)
        }
    }


    private fun updateData() {
        Log.d(TAG, "Updating...")
    }

    private fun saveData() {
        Log.d(TAG, "Saving...")
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