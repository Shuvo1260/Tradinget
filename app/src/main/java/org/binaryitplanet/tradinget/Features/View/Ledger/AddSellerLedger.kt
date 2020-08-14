package org.binaryitplanet.tradinget.Features.View.Ledger

import android.app.DatePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import org.binaryitplanet.tradinget.Features.Prsenter.SellerLedgerPresenterIml
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.SellerLedgerUtils
import org.binaryitplanet.tradinget.Utils.StakeholderUtils
import org.binaryitplanet.tradinget.databinding.ActivityAddSellerLedgerBinding
import java.util.*

class AddSellerLedger : AppCompatActivity(), SellerLedgerView {

    private val TAG = "AddSellerLedger"
    private lateinit var binding: ActivityAddSellerLedgerBinding

    private var month: String? = null
    private var year: String? = null
    private var transactionType: String? = null
    private var paymentType: String? = null
    private var amount: String? = null
    private var remark: String? = null
    private var issueDate: String? = null


    private var issueDay: Int = 0
    private var issueMonth: Int = 0
    private var issueYear: Int = 0
    private var dateMillis: Long = 0
    private var rentDateMillis: Long = 0
    private lateinit var months: Array<String>

    private lateinit var seller: StakeholderUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_seller_ledger)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_seller_ledger)

        setUpToolbar()

        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.done && checkValidity()) {
                saveData()
            }
            return@setOnMenuItemClickListener super.onOptionsItemSelected(it)
        }


        getCurrentDate()

        setUpDropDown()

        setUpIssueDate()

        setUpIssueDateListener()
    }

    override fun onResume() {
        super.onResume()
        seller = intent?.getSerializableExtra(Config.STAKEHOLDER) as StakeholderUtils
    }

    private fun saveData() {

        rentDateMillis = year?.toLong()!! * 100 + months.indexOf(month)

        val sellerledger = SellerLedgerUtils(
            null,
            seller.id,
            transactionType!!,
            paymentType!!,
            amount!!.toDouble(),
            issueMonth,
            issueYear,
            remark!!,
            issueDate!!,
            dateMillis
        )

        val presenter = SellerLedgerPresenterIml(this, this)
        presenter.insertLedger(sellerledger)
    }

    override fun onInsertLedgerListener(status: Boolean) {
        super.onInsertLedgerListener(status)
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
                Config.SUCCESS_MESSAGE,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Checking validity
    private fun checkValidity(): Boolean {
        transactionType = binding.type.text.toString()
        amount = binding.amount.text.toString()
        month = binding.month.text.toString()
        year = binding.year.text.toString()
        remark = binding.remark.text.toString()
        paymentType = binding.paymentType.text.toString()

        if (transactionType.isNullOrEmpty()) {
            binding.type.error = Config.REQUIRED_FIELD
            binding.type.requestFocus()
            return false
        }
        if (paymentType.isNullOrEmpty()) {
            binding.paymentType.error = Config.REQUIRED_FIELD
            binding.paymentType.requestFocus()
            return false
        }

        if (amount.isNullOrEmpty()) {
            binding.amount.error = Config.REQUIRED_FIELD
            binding.amount.requestFocus()
            return false
        }
        if (month.isNullOrEmpty()) {
            binding.month.error = Config.REQUIRED_FIELD
            binding.month.requestFocus()
            return false
        }
        if (year.isNullOrEmpty()) {
            binding.year.error = Config.REQUIRED_FIELD
            binding.year.requestFocus()
            return false
        }

        return true
    }

    private fun getCurrentDate() {
        val calendar = Calendar.getInstance()
        issueDay = calendar.get(Calendar.DAY_OF_MONTH)
        issueMonth = calendar.get(Calendar.MONTH)
        issueYear = calendar.get(Calendar.YEAR)
    }
    private fun setUpIssueDateListener() {
        binding.issueDate.setOnClickListener {
            DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                issueDay = dayOfMonth
                issueMonth = month
                issueYear = year
                setUpIssueDate()
            }, issueYear, issueMonth, issueDay).show()
        }
    }

    private fun setUpIssueDate() {
        dateMillis = issueYear * 10000L +
                issueMonth * 100L +
                issueDay
        issueDate = "%02d/%02d/%04d".format(
            issueDay,
            issueMonth+1,
            issueYear
        )
        binding.issueDate.text = issueDate
    }

    private fun setUpDropDown() {
        var transactionTypes = resources.getStringArray(R.array.transactionType)
        months = resources.getStringArray(R.array.months)
        var years = resources.getStringArray(R.array.years)
        var paymentTypes = resources.getStringArray(R.array.paymentType)

        var transactionAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            transactionTypes
        )
        var monthsAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            months
        )
        var yearsAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            years
        )

        var paymentAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            paymentTypes
        )


        binding.type.setText(transactionTypes[0])
        binding.paymentType.setText(paymentTypes[0])
        binding.month.setText(months[issueMonth])
        binding.year.setText(issueYear.toString())

        binding.type.setAdapter(transactionAdapter)
        binding.paymentType.setAdapter(paymentAdapter)
        binding.month.setAdapter(monthsAdapter)
        binding.year.setAdapter(yearsAdapter)
    }


    // Toolbar menu setting
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.done_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    private fun setUpToolbar() {

        binding.toolbar.title = Config.TOOLBAR_TITLE_ADD_SELLER_LEDGER
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