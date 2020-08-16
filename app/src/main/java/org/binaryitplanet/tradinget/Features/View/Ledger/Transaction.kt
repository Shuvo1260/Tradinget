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
import org.binaryitplanet.tradinget.Features.Prsenter.BuyerLedgerPresenterIml
import org.binaryitplanet.tradinget.Features.Prsenter.LedgerPresenter
import org.binaryitplanet.tradinget.Features.Prsenter.LedgerPresenterIml
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.BuyerLedgerUtils
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.LedgerUtils
import org.binaryitplanet.tradinget.databinding.ActivityTransactionBinding
import java.util.*

class Transaction : AppCompatActivity(), TransactionView {



    private val TAG = "AddBuyer"
    private lateinit var binding: ActivityTransactionBinding
    private lateinit var ledger: LedgerUtils
    private lateinit var amount: String
    private lateinit var transactionType: String
    private lateinit var paymentType: String
    private lateinit var remark: String
    private var flag: Boolean = true
    private var issueDate: String? = null


    private var issueDay: Int = 0
    private var issueMonth: Int = 0
    private var issueYear: Int = 0
    private var dateMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transaction)


        setUpToolbar()

        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.done && checkValidity()) {
                if (flag) {
                    if (transactionType == Config.CREDIT) {
                        ledger.brokerAmountPaid += amount.toDouble()
                        ledger.brokerAmountRemaining -= amount.toDouble()
                    } else {
                        ledger.brokerAmountPaid -= amount.toDouble()
                        ledger.brokerAmountRemaining += amount.toDouble()
                    }
                } else {
                    if (transactionType == Config.CREDIT) {
                        ledger.paidAmount += amount.toDouble()
                    } else {
                        ledger.paidAmount -= amount.toDouble()
                    }
                }
                saveLedger()
            }
            return@setOnMenuItemClickListener super.onOptionsItemSelected(it)
        }

        setViews()

        getCurrentDate()

        setUpIssueDate()

        setUpIssueDateListener()
    }

    private fun saveLedger() {
        val ledgerUtils = BuyerLedgerUtils(
            null,
            ledger.id,
            transactionType,
            paymentType,
            amount.toDouble(),
            remark,
            issueDate!!,
            dateMillis,
            flag
        )
        val presenter = BuyerLedgerPresenterIml(this, this)
        presenter.insertBuyerLedger(ledger, ledgerUtils)
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
                Config.FAILED_MESSAGE,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun checkValidity(): Boolean {
        transactionType = binding.transactionType.text.toString()
        paymentType = binding.paymentType.text.toString()
        amount = binding.amount.text.toString()
        remark = binding.remark.text.toString()

        if (amount.isNullOrEmpty()) {
            binding.amount.error = Config.REQUIRED_FIELD
            binding.amount.requestFocus()
            return false
        }
        if (transactionType.isNullOrEmpty()) {
            binding.transactionType.error = Config.REQUIRED_FIELD
            binding.transactionType.requestFocus()
            return false
        }
        return true
    }

    private fun setViews() {
        var type = arrayListOf<String>(Config.CREDIT, Config.DEBIT)
        var paymentTypes = resources.getStringArray(R.array.paymentType)
        Log.d(TAG, "Types: $type")

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, type)
        binding.transactionType.setText(type[0])
        binding.transactionType.setAdapter(adapter)

        val paymentAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, paymentTypes)
        binding.paymentType.setText(paymentTypes[0])
        binding.paymentType.setAdapter(paymentAdapter)
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

    override fun onResume() {
        super.onResume()

        ledger = intent?.getSerializableExtra(Config.LEDGER) as LedgerUtils
        flag = intent?.getBooleanExtra(Config.OPERATION_FLAG, true)!!
    }


    // Toolbar menu setting
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.done_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setUpToolbar() {

        binding.toolbar.title = "Transaction"

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