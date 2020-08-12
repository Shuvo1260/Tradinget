package org.binaryitplanet.tradinget.Features.View.Ledger

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import org.binaryitplanet.tradinget.Features.Adapter.BrokerDropDownAdapter
import org.binaryitplanet.tradinget.Features.Adapter.LedgerPacketAdapter
import org.binaryitplanet.tradinget.Features.Adapter.PacketDropDownAdapter
import org.binaryitplanet.tradinget.Features.Adapter.SubPacketDropDownAdapter
import org.binaryitplanet.tradinget.Features.Common.StakeholderView
import org.binaryitplanet.tradinget.Features.Prsenter.LedgerPresenterIml
import org.binaryitplanet.tradinget.Features.Prsenter.PacketDetailsPresenterIml
import org.binaryitplanet.tradinget.Features.Prsenter.PacketPresenterIml
import org.binaryitplanet.tradinget.Features.Prsenter.StakeholderPresenterIml
import org.binaryitplanet.tradinget.Features.View.Inventory.InventoryView
import org.binaryitplanet.tradinget.Features.View.Inventory.ViewPacketDetails
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.*
import org.binaryitplanet.tradinget.databinding.ActivityAddLedgerBinding
import java.util.*

@Suppress("DEPRECATION")
class AddLedger : AppCompatActivity(), InventoryView, ViewPacketDetails, StakeholderView, ViewLedgers {


    private val TAG = "AddLedger"
    private lateinit var binding: ActivityAddLedgerBinding
    private lateinit var packetList: ArrayList<PacketUtils>
    private lateinit var subPacketList: ArrayList<PacketDetailsUtils>
    private lateinit var allSubPacketList: ArrayList<PacketDetailsUtils>
    private lateinit var brokerList: ArrayList<StakeholderUtils>
    private lateinit var stakeholder: StakeholderUtils
    private var soldPacketList = arrayListOf<SoldPacketUtils>()
    private var packetPosition: Int = -1
    private var subPacketPosition: Int = -1
    private var brokerPosition: Int = -1
    private var day: Int = 0
    private var month: Int = 0
    private var year: Int = 0
    private var dueDay: Int = 0
    private var dueMonth: Int = 0
    private var dueYear: Int = 0
    private lateinit var weight: String
    private lateinit var rate: String

    private lateinit var dateString: String
    private lateinit var dueDateString: String

    private var totalWeight: Double = 0.0
    private var totalRate: Double = 0.0
    private var totalPrice: Double = 0.0

    private var imageURL: String? = null
    private var ledgerId: String? = null

    private var brokerPercentage: Double = 0.0
    private var brokerAmount: Double = 0.0
    private var discountPercentage: Double = 0.0
    private var discountAmount: Double = 0.0

    private lateinit var remark: String
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ledger)

        ledgerId = Calendar.getInstance().timeInMillis.toString()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_ledger)

        progressDialog = ProgressDialog(this)

        setUpToolbar()
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.done && checkValidity()) {
                Log.d(TAG, "Size: ${subPacketList.size}")
                saveData()
            }
            return@setOnMenuItemClickListener super.onOptionsItemSelected(it)
        }

        binding.addPacket.setOnClickListener {
            if (isPacketValid()) {
                var subPacketNumber = ""
                var sieve = ""
                if (subPacketPosition != -1) {
                    subPacketNumber = subPacketList[subPacketPosition].packetDetailsNumber
                    sieve = subPacketList[subPacketPosition].sieve
                }
                soldPacketList.add(
                    SoldPacketUtils(
                        null,
                        ledgerId,
                        packetList[packetPosition].packetNumber,
                        packetList[packetPosition].packetName,
                        subPacketNumber,
                        sieve,
                        weight.toDouble(),
                        rate.toDouble(),
                        packetList[packetPosition].code,
                        packetPosition,
                        subPacketPosition
                    )
                )
                totalWeight += weight.toDouble()
                totalRate += rate.toDouble()
                totalPrice += (weight.toDouble() * rate.toDouble())
                Log.d(TAG, "Price: $totalPrice $totalWeight $totalRate")
                setPacketList()
            }
        }

        binding.addImage.setOnClickListener {
            val galleryIntent = Intent()
            galleryIntent.type = "image/*"
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            val intent = Intent.createChooser(galleryIntent, Config.PICK_IMAGE)
            startActivityForResult(intent, Config.PICK_IMAGE_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK
            && requestCode == Config.PICK_IMAGE_REQUEST_CODE
            && data != null
            && data.data != null
        ) {
            val imageUri = data.data
            imageURL = imageUri.toString()
            Log.d(TAG, "ImagePath: $imageURL")
            binding.addImage.setImageURI(imageUri)

        }
    }

    private fun setPacketList() {
        val adapter = LedgerPacketAdapter(
            this,
            soldPacketList,
            this
        )

        binding.packetList.adapter = adapter
        binding.packetList.layoutManager = LinearLayoutManager(this)
        binding.packetList.setItemViewCacheSize(Config.LIST_CACHED_SIZE)

        binding.packet.setText("")
        binding.subPacket.setText("")
        binding.weight.setText("")
        binding.rate.setText("")

        packetPosition = -1
        subPacketPosition = -1

        subPacketList.clear()

        setSubPacketDropDownAdapter()
    }

    private fun isPacketValid(): Boolean {
        weight = binding.weight.text.toString()
        rate = binding.rate.text.toString()

        if (packetPosition == -1) {
            binding.packet.error = Config.REQUIRED_FIELD
            binding.packet.requestFocus()
            return false
        }
//        if (subPacketPosition == -1) {
//            binding.subPacket.error = Config.REQUIRED_FIELD
//            binding.subPacket.requestFocus()
//            return false
//        }

        if (weight.isNullOrEmpty()) {
            binding.weight.error = Config.REQUIRED_FIELD
            binding.weight.requestFocus()
            return false
        }

        if (rate.isNullOrEmpty()) {
            binding.rate.error = Config.REQUIRED_FIELD
            binding.rate.requestFocus()
            return false
        }

        return true
    }

    override fun onResume() {
        super.onResume()

        stakeholder = intent?.getSerializableExtra(Config.STAKEHOLDER) as StakeholderUtils

        val  presenter = PacketPresenterIml(this, this)
        presenter.fetchPacketList()

        val brokerPresenter = StakeholderPresenterIml(this, this)
        brokerPresenter.fetchStakeholder(Config.TYPE_ID_BROKER)


        val subPacketPresenter = PacketDetailsPresenterIml(this, this)
        subPacketPresenter.fetchAllPacketDetailsList()

        setupDate()
    }

    override fun onFetchAllPacketDetailsListListener(subPacketList: List<PacketDetailsUtils>) {
        super.onFetchAllPacketDetailsListListener(subPacketList)
        allSubPacketList = subPacketList as ArrayList<PacketDetailsUtils>
    }

    private fun setupDate() {
        var calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        dueDay = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        dueMonth = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        dueYear = calendar.get(Calendar.YEAR)
        dateString = getFormattedDate(day, month, year).toString()
        binding.date.text = dateString
        dueDateString = getFormattedDate(dueDay, dueMonth, dueYear).toString()
        binding.dueDate.text = dueDateString

        binding.date.setOnClickListener {
            var datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                day = dayOfMonth
                this.month = month
                this.year = year
                Log.d(TAG, "Date: $dayOfMonth/$month/$year")
                dateString = getFormattedDate(day, month, year).toString()
                binding.date.text = dateString
            }, year, month, day)
            datePickerDialog.show()
        }
        binding.dueDate.setOnClickListener {

            Log.d(TAG, "Date: $dueDay/$dueMonth/$dueYear")
            var datePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    dueDay = dayOfMonth
                    dueMonth = month
                    dueYear = year
                    dueDateString = getFormattedDate(dueDay, dueMonth, dueYear).toString()
                    binding.dueDate.text = dueDateString
                }, dueYear, dueMonth, dueDay)
            datePickerDialog.show()
        }
    }

    private fun getFormattedDate(day: Int, month: Int, year: Int): CharSequence? {
        return "%02d/%02d/%04d".format(
            day,
            month+1,
            year
        )
    }

    override fun onFetchPacketListListener(PacketList: List<PacketUtils>) {
        super.onFetchPacketListListener(PacketList)
        packetList = PacketList as ArrayList<PacketUtils>
        Log.d(TAG, "PacketList: $packetList")
        setPacketDropDownAdapter()
        binding.packet.setOnItemClickListener { parent, view, position, id ->
            binding.packet.setText(packetList[position].packetName)
            binding.subPacket.text = null
            packetPosition = position
            subPacketPosition = -1
            val presenter = PacketDetailsPresenterIml(this, this)
            presenter.fetchPacketDetailsList(packetList[position].packetNumber)
        }
    }

    private fun setPacketDropDownAdapter() {
        val adapter = PacketDropDownAdapter(this, packetList)
        binding.packet.setAdapter(adapter)
    }

    override fun onFetchPacketDetailsListListener(packetDetailsList: List<PacketDetailsUtils>) {
        super.onFetchPacketDetailsListListener(packetDetailsList)
        subPacketList = packetDetailsList as ArrayList<PacketDetailsUtils>
        Log.d(TAG, "SubPacketList: ${subPacketList.size}")
        setSubPacketDropDownAdapter()
        binding.subPacket.setOnItemClickListener { parent, view, position, id ->
            subPacketPosition = allSubPacketList.indexOf(subPacketList[position])
            binding.subPacket.setText(subPacketList[position].packetDetailsNumber)
        }
    }

    private fun setSubPacketDropDownAdapter() {
        val adapter = SubPacketDropDownAdapter(this, subPacketList)
        binding.subPacket.setAdapter(adapter)
    }

    override fun onFetchStakeholderListListener(stakeholderList: List<StakeholderUtils>) {
        super.onFetchStakeholderListListener(stakeholderList)
        brokerList = stakeholderList as ArrayList<StakeholderUtils>
        val adapter = BrokerDropDownAdapter(this, brokerList)
        binding.broker.setAdapter(adapter)
        binding.broker.setOnItemClickListener { parent, view, position, id ->
            brokerPosition = position
            binding.broker.setText(brokerList[position].name)
        }
    }

    override fun onPacketDeleteListener(position: Int) {
        super.onPacketDeleteListener(position)
        totalWeight -= soldPacketList[position].weight
        totalRate -= soldPacketList[position].rate
        totalPrice -= (soldPacketList[position].weight * soldPacketList[position].rate)
        Log.d(TAG, "Price: $totalPrice $totalWeight $totalRate")
        soldPacketList.removeAt(position)
        setPacketList()
    }

    private fun saveData() {
        Log.d(TAG, "Saving...")

        progressDialog.setTitle(Config.CREATING_LEDGER_TITLE)
        progressDialog.setMessage(Config.CREATING_LEDGER_MESSAGE)
        progressDialog.show()

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val dateMilli = calendar.timeInMillis
        calendar.set(dueYear, dueMonth, dueDay)
        val dueDateMilli = calendar.timeInMillis

        val ledgerUtils = LedgerUtils(
            null,
            ledgerId,
            stakeholder.id!!,
            brokerList[brokerPosition].id!!,
            brokerList[brokerPosition].name,
            brokerPercentage,
            brokerAmount,
            0.0,
            brokerAmount,
            totalPrice + brokerAmount,
            discountPercentage,
            discountAmount,
            totalWeight,
            totalPrice - discountAmount,
            0.0,
            soldPacketList.size,
            Calendar.getInstance().get(Calendar.MONTH),
            dateString,
            dateMilli,
            dueDateString,
            dueDateMilli,
            remark,
            imageURL,
            null
        )
        Log.d(TAG, "Size: ${subPacketList.size}")

        val presenter = LedgerPresenterIml(this, this)
        presenter.insertLedger(
            ledgerUtils,
            soldPacketList,
            packetList,
            allSubPacketList
        )
    }

    override fun onLedgerInsertListener(status: Boolean) {
        super.onLedgerInsertListener(status)
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
        progressDialog.dismiss()
    }

    private fun checkValidity(): Boolean {
        if (soldPacketList.size < 1) {
            binding.packet.error = Config.REQUIRED_FIELD
            binding.packet.requestFocus()
            return false
        }

        if (brokerPosition == -1) {
            binding.broker.error = Config.REQUIRED_FIELD
            binding.broker.requestFocus()
            return false
        }

        remark = binding.remark.text.toString()

        if (binding.brokerPercentage.text.toString().isNullOrEmpty()) {
            binding.brokerPercentage.error = Config.REQUIRED_FIELD
            binding.brokerPercentage.requestFocus()
            return false
        }
        if (binding.brokerAmount.text.toString().isNullOrEmpty()) {
            binding.brokerAmount.error = Config.REQUIRED_FIELD
            binding.brokerAmount.requestFocus()
            return false
        }
        if (binding.discountPercentage.text.toString().isNullOrEmpty()) {
            binding.discountPercentage.error = Config.REQUIRED_FIELD
            binding.discountPercentage.requestFocus()
            return false
        }
        if (binding.discountAmount.text.toString().isNullOrEmpty()) {
            binding.discountAmount.error = Config.REQUIRED_FIELD
            binding.discountAmount.requestFocus()
            return false
        }


        brokerPercentage = binding.brokerPercentage.text.toString().toDouble()
        brokerAmount = binding.brokerAmount.text.toString().toDouble()
        discountPercentage = binding.discountPercentage.text.toString().toDouble()
        discountAmount = binding.discountAmount.text.toString().toDouble()
        return true
    }

    // Toolbar menu setting
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.done_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setUpToolbar() {

        binding.toolbar.title = Config.TOOLBAR_TITLE_ADD_LEDGER

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