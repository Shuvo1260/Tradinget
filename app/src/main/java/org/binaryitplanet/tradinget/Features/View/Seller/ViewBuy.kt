package org.binaryitplanet.tradinget.Features.View.Seller

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import org.binaryitplanet.rentalreminderapp.Features.Adapter.SellerLedgerListAdapter
import org.binaryitplanet.tradinget.Features.Prsenter.BuyPresenterIml
import org.binaryitplanet.tradinget.Features.Prsenter.LedgerPresenterIml
import org.binaryitplanet.tradinget.Features.Prsenter.SellerLedgerPresenterIml
import org.binaryitplanet.tradinget.Features.View.Ledger.AddLedger
import org.binaryitplanet.tradinget.Features.View.Ledger.AddSellerLedger
import org.binaryitplanet.tradinget.Features.View.Ledger.SellerLedgerView
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.BuyUtils
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.SellerLedgerUtils
import org.binaryitplanet.tradinget.databinding.ActivityViewBuyBinding

class ViewBuy : AppCompatActivity(), BuyView, SellerLedgerView {

    private val TAG = "ViewBuy"
    private lateinit var binding: ActivityViewBuyBinding
    private lateinit var buy: BuyUtils
    private var ledgerList = arrayListOf<SellerLedgerUtils>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_buy)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_buy)

        buy = intent?.getSerializableExtra(Config.BUY) as BuyUtils

        setUpToolbar()


        binding.add.setOnClickListener {
            val intent = Intent(this, AddSellerLedger::class.java)
            intent.putExtra(Config.BUY, buy)
            startActivity(intent)
            overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft)
        }



        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.edit) {
                editData()
            } else if (it.itemId == R.id.delete) {
                deleteData()
            }
            return@setOnMenuItemClickListener super.onOptionsItemSelected(it)
        }
    }

    private fun editData() {
        val intent = Intent(this, BuyProduct::class.java)
        intent.putExtra(Config.OPERATION_FLAG, false)
        intent.putExtra(Config.STAKEHOLDER, buy)
        startActivity(intent)
        overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft)
    }

    private fun deleteData() {

        val builder = AlertDialog.Builder(this)

        builder.setTitle(Config.DELETE_LEDGER_TITLE)
        builder.setMessage(Config.DELETE_LEDGER_MESSAGE)

        builder.setIcon(R.drawable.ic_launcher)

        builder.setPositiveButton(Config.YES_MESSAGE){
                dialog: DialogInterface?, which: Int ->
            val presenter = BuyPresenterIml(this, this)
            presenter.deleteBuy(buy)
        }

        builder.setNegativeButton(
            Config.NO_MESSAGE
        ){
                dialog: DialogInterface?, which: Int ->
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onResume() {
        super.onResume()
        val presenter = BuyPresenterIml(this, this)
        presenter.fetchBuyById(buy.id!!)

        val ledgerPresenter = SellerLedgerPresenterIml(this, this)
        ledgerPresenter.fetchLedgerListById(buy.id!!)
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
            presenter.fetchLedgerListById(buy.id!!)
        } else {
            Toast.makeText(
                this,
                Config.FAILED_MESSAGE,
                Toast.LENGTH_SHORT
            ).show()
        }
    }



    override fun fetchBuyListener(buy: BuyUtils) {
        super.fetchBuyListener(buy)
        this.buy = buy
        setViews()
    }

    override fun onFetchLedgerListListener(ledgerList: List<SellerLedgerUtils>) {
        super.onFetchLedgerListListener(ledgerList)
        this.ledgerList = ledgerList as ArrayList<SellerLedgerUtils>
        val adapter = SellerLedgerListAdapter(this, this.ledgerList, this)
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.setItemViewCacheSize(Config.LIST_CACHED_SIZE)

        var totaCredit = 0.0
        var totaDebit = 0.0

        this.ledgerList.forEach {
            if (it.transactionType == Config.CREDIT) {
                totaCredit += it.amount
            } else {
                totaDebit += it.amount
            }
        }

        val dueAmount = buy.amount - (totaDebit - totaCredit)

        binding.totalCredit.text = "Total credit: ${Config.RUPEE_SIGN} $totaCredit"
        binding.totalDebit.text = "Total debit: ${Config.RUPEE_SIGN} $totaDebit"
        binding.dueAmount.text = "Due: ${Config.RUPEE_SIGN} $dueAmount"
    }

    private fun setViews() {
        binding.name.text = "Broker name: ${getValue(buy.brokerName)}"
        binding.weight.text = "Weight: ${buy.weight} ${Config.CTS}"
        binding.rate.text = "Rate: ${Config.RUPEE_SIGN} ${buy.rate}"
        binding.discountAmount.text = "Discount: ${Config.RUPEE_SIGN} ${buy.discountAmount}"
        binding.amount.text = "Total: ${Config.RUPEE_SIGN} ${buy.amount}"
        binding.purchaseDate.text = buy.purchaseDate
        binding.dueDate.text = buy.dueDate
        binding.remark.text = buy.remark
    }

    private fun getValue(brokerName: String?): String {
        return if (brokerName.isNullOrEmpty())
            "N.A."
        else
            brokerName
    }


    // Toolbar menu setting

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.view_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    private fun setUpToolbar() {

        binding.toolbar.title = Config.TOOLBAR_TITLE_BOUGHT_PRODUCT

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