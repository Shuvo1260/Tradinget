package org.binaryitplanet.tradinget.Features.View.Ledger

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import org.binaryitplanet.tradinget.Features.Prsenter.LedgerPresenter
import org.binaryitplanet.tradinget.Features.Prsenter.LedgerPresenterIml
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.LedgerUtils
import org.binaryitplanet.tradinget.databinding.ActivityTransactionBinding

class Transaction : AppCompatActivity(), ViewLedgers {



    private val TAG = "AddBuyer"
    private lateinit var binding: ActivityTransactionBinding
    private lateinit var ledger: LedgerUtils
    private lateinit var amount: String
    private lateinit var transactionType: String
    private var flag: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transaction)


        setUpToolbar()

        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.done && checkValidity()) {
                if (flag) {
                    if (transactionType == Config.CREDIT) {
                        ledger.paidAmount += amount.toDouble()
                    } else {
                        ledger.paidAmount -= amount.toDouble()
                    }

                } else {
                    if (transactionType == Config.CREDIT) {
                        ledger.brokerAmountPaid += amount.toDouble()
                        ledger.brokerAmountRemaining -= amount.toDouble()
                    } else {
                        ledger.brokerAmountRemaining -= amount.toDouble()
                        ledger.brokerAmountRemaining += amount.toDouble()
                    }
                }
                val presenter = LedgerPresenterIml(this, this)
                presenter.updateLedger(ledger)
            }
            return@setOnMenuItemClickListener super.onOptionsItemSelected(it)
        }

        setViews()
    }

    override fun onLedgerUpdateListener(status: Boolean) {
        super.onLedgerUpdateListener(status)
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
        amount = binding.amount.text.toString()

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
        Log.d(TAG, "Types: $type")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, type)
        binding.transactionType.setAdapter(adapter)
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