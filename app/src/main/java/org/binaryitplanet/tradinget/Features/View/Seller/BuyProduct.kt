package org.binaryitplanet.tradinget.Features.View.Seller

import android.app.DatePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import org.binaryitplanet.tradinget.Features.Adapter.BrokerDropDownAdapter
import org.binaryitplanet.tradinget.Features.Common.StakeholderView
import org.binaryitplanet.tradinget.Features.Prsenter.BuyPresenterIml
import org.binaryitplanet.tradinget.Features.Prsenter.StakeholderPresenterIml
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.BuyUtils
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.StakeholderUtils
import org.binaryitplanet.tradinget.databinding.ActivityBuyProductBinding
import java.util.*

class BuyProduct : AppCompatActivity(), StakeholderView, BuyView {
    private val TAG = "BuyProduct"
    private lateinit var binding: ActivityBuyProductBinding
    private lateinit var seller: StakeholderUtils
    private var weight: Double = 0.0
    private var rate: Double = 0.0
    private var amount: Double = 0.0
    private var discountAmount: Double = 0.0
    private var brokerName: String = ""
    private lateinit var remark: String
    private lateinit var purchaseDate: String
    private lateinit var dueDate: String
    private var purchaseDateMilli: Long = 0
    private var dueDateMilli: Long = 0

    private var purchaseDay: Int = 0
    private var purchaseMonth: Int = 0
    private var purchaseYear: Int = 0
    private var dueDay: Int = 0
    private var dueMonth: Int = 0
    private var dueYear: Int = 0

    private var brokerPosition = -1
    private var brokerList = arrayListOf<StakeholderUtils>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_product)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_buy_product)

        setUpToolbar()


        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.done && checkValidity()) {
                saveData()
            }
            return@setOnMenuItemClickListener super.onOptionsItemSelected(it)
        }

        setupDate()
    }

    private fun saveData() {
        var brokerId = 0L
        if (brokerPosition != -1)
            brokerId = brokerList[brokerPosition].id!!

        val buyUtils = BuyUtils(
            null,
            seller.id,
            weight,
            rate,
            discountAmount,
            amount,
            purchaseDate,
            purchaseDateMilli,
            dueDate,
            dueDateMilli,
            brokerName,
            brokerId,
            remark
        )
        val presenter = BuyPresenterIml(this, this)
        presenter.insertBuy(buyUtils)
    }

    override fun insertBuyListener(status: Boolean) {
        super.insertBuyListener(status)
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
        remark = binding.remark.text.toString()
        brokerName = binding.broker.text.toString()

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

        if (!binding.discountAmount.text.isNullOrEmpty()){
            discountAmount = binding.discountAmount.text.toString().toDouble()
        }
        weight = binding.weight.text.toString().toDouble()
        rate = binding.rate.text.toString().toDouble()
        amount = (weight * rate) - discountAmount
        return true
    }


    override fun onResume() {
        super.onResume()
        seller = intent?.getSerializableExtra(Config.STAKEHOLDER) as StakeholderUtils

        val brokerPresenter = StakeholderPresenterIml(this, this)
        brokerPresenter.fetchStakeholder(Config.TYPE_ID_BROKER)
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

    private fun setupDate() {
        var calendar = Calendar.getInstance()
        purchaseDay = calendar.get(Calendar.DAY_OF_MONTH)
        dueDay = calendar.get(Calendar.DAY_OF_MONTH)
        purchaseMonth = calendar.get(Calendar.MONTH)
        dueMonth = calendar.get(Calendar.MONTH)
        purchaseYear = calendar.get(Calendar.YEAR)
        dueYear = calendar.get(Calendar.YEAR)
        purchaseDate = getFormattedDate(purchaseDay, purchaseMonth, purchaseYear).toString()
        binding.purchaseDate.text = purchaseDate
        dueDate = getFormattedDate(dueDay, dueMonth, dueYear).toString()
        binding.paymentDate.text = dueDate

        binding.purchaseDate.setOnClickListener {
            var datePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    purchaseDay = dayOfMonth
                    purchaseMonth = month
                    purchaseYear = year
                    Log.d(TAG, "Date: $dayOfMonth/$month/$year")
                    purchaseDate = getFormattedDate(purchaseDay, purchaseMonth, purchaseYear).toString()
                    binding.purchaseDate.text = purchaseDate
                    purchaseDateMilli = getDateMilli(dayOfMonth, month, year)
                }, purchaseYear, purchaseMonth, purchaseDay)
            datePickerDialog.show()
        }
        binding.paymentDate.setOnClickListener {

            Log.d(TAG, "Date: $dueDay/$dueMonth/$dueYear")
            var datePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    dueDay = dayOfMonth
                    dueMonth = month
                    dueYear = year
                    dueDate = getFormattedDate(dueDay, dueMonth, dueYear).toString()
                    binding.paymentDate.text = dueDate
                    dueDateMilli = getDateMilli(dayOfMonth, month, year)
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

    private fun getDateMilli(day: Int, month: Int, year: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar.timeInMillis
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