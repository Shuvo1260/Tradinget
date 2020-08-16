package org.binaryitplanet.tradinget.Features.View.Seller

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
import androidx.recyclerview.widget.LinearLayoutManager
import org.binaryitplanet.rentalreminderapp.Features.Adapter.SellerLedgerListAdapter
import org.binaryitplanet.tradinget.Features.Adapter.BuyAdapter
import org.binaryitplanet.tradinget.Features.Adapter.LedgerAdapter
import org.binaryitplanet.tradinget.Features.Common.StakeholderView
import org.binaryitplanet.tradinget.Features.Prsenter.BuyPresenterIml
import org.binaryitplanet.tradinget.Features.Prsenter.LedgerPresenterIml
import org.binaryitplanet.tradinget.Features.Prsenter.SellerLedgerPresenterIml
import org.binaryitplanet.tradinget.Features.Prsenter.StakeholderPresenterIml
import org.binaryitplanet.tradinget.Features.View.Broker.AddBroker
import org.binaryitplanet.tradinget.Features.View.Ledger.AddLedger
import org.binaryitplanet.tradinget.Features.View.Ledger.AddSellerLedger
import org.binaryitplanet.tradinget.Features.View.Ledger.SellerLedgerView
import org.binaryitplanet.tradinget.Features.View.Ledger.ViewLedgers
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.*
import org.binaryitplanet.tradinget.databinding.ActivityViewSellerBinding

class ViewSeller : AppCompatActivity(), StakeholderView, BuyView, SellerLedgerView {

    private val TAG = "ViewSeller"
    private lateinit var binding: ActivityViewSellerBinding
    private lateinit var stakeholder: StakeholderUtils
    private var ledgerList = arrayListOf<SellerLedgerUtils>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_seller)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.enterTransition = null
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_seller)

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
            val intent = Intent(this, BuyProduct::class.java)
            intent.putExtra(Config.STAKEHOLDER, stakeholder)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val presenter = StakeholderPresenterIml(this, this)
        presenter.fetchStakeholderById(stakeholder.id!!)

        val buyPresenter = BuyPresenterIml(this, this)
        buyPresenter.fetchBuyListBySellerId(stakeholder.id!!)
    }

    override fun fetchBuyListListener(buyList: List<BuyUtils>) {
        super.fetchBuyListListener(buyList)
        Log.d(TAG, "BuyList: $buyList")

        val adapter = BuyAdapter(this, buyList as ArrayList<BuyUtils>)
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.setItemViewCacheSize(Config.LIST_CACHED_SIZE)

    }

    override fun onFetchStakeholderListener(stakeholder: StakeholderUtils) {
        super.onFetchStakeholderListener(stakeholder)
        this.stakeholder = stakeholder
        setupViews()
    }

    override fun onSellerLedgerDeleteClick(position: Int) {
        super.onSellerLedgerDeleteClick(position)
        Log.d(TAG, "DeleteParticular: $position")
        val builder = AlertDialog.Builder(this)

        builder.setTitle(Config.DELETE_LEDGER_TITLE)
        builder.setMessage(Config.DELETE_LEDGER_MESSAGE)

        builder.setIcon(R.drawable.ic_launcher)

        builder.setPositiveButton(
            Config.YES_MESSAGE
        ){
                dialog: DialogInterface?, which: Int ->



            val presenter = SellerLedgerPresenterIml(this, this)
            presenter.deleteLedger(ledgerList[position])
        }

        builder.setNegativeButton(
            Config.NO_MESSAGE
        ){
                dialog: DialogInterface?, which: Int ->
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onDeleteLedgerListener(status: Boolean) {
        super.onDeleteLedgerListener(status)
        if (status) {
            Toast.makeText(
                this,
                Config.SUCCESS_MESSAGE,
                Toast.LENGTH_SHORT
            ).show()
            val presenter = SellerLedgerPresenterIml(this, this)
            presenter.fetchLedgerListBySellerId(stakeholder.id!!)
        } else {
            Toast.makeText(
                this,
                Config.FAILED_MESSAGE,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun deleteData() {

        val builder = AlertDialog.Builder(this)

        builder.setTitle(Config.DELETE_SELLER_TITLE)
        builder.setMessage(Config.DELETE_SELLER_MESSAGE)

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
        binding.firmName.text = Config.FIRM_NAME + ": " + stakeholder.firmName
        binding.address.text = Config.ADDRESS + ": " + stakeholder.address
        binding.stateCode.text = Config.STATE_CODE + ": " + stakeholder.stateCode
        binding.gstNumber.text = Config.GST_NUMBER + ": " + stakeholder.gstNumber
        binding.panNumber.text = Config.PAN_NUMBER + ": " + stakeholder.panNumber

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