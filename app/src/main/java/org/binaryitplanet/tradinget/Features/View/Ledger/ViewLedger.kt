package org.binaryitplanet.tradinget.Features.View.Ledger

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_view_ledger.*
import org.binaryitplanet.rentalreminderapp.Features.Adapter.BuyerLedgerListAdapter
import org.binaryitplanet.tradinget.Features.Adapter.SoldPacketAdapter
import org.binaryitplanet.tradinget.Features.Common.StakeholderView
import org.binaryitplanet.tradinget.Features.Prsenter.BuyerLedgerPresenterIml
import org.binaryitplanet.tradinget.Features.Prsenter.LedgerPresenterIml
import org.binaryitplanet.tradinget.Features.Prsenter.SellerLedgerPresenterIml
import org.binaryitplanet.tradinget.Features.Prsenter.StakeholderPresenterIml
import org.binaryitplanet.tradinget.Features.View.Invoice.CreateInvoice
import org.binaryitplanet.tradinget.Features.View.Invoice.InvoiceBuilder
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.*
import org.binaryitplanet.tradinget.databinding.ActivityViewLedgerBinding

class ViewLedger : AppCompatActivity(), ViewLedgers, StakeholderView, TransactionView{
    private val TAG = "ViewLedger"
    private lateinit var binding: ActivityViewLedgerBinding

    private lateinit var ledger: LedgerUtils
    private lateinit var stakeholder: StakeholderUtils
    private var isBroker: Boolean = false
    private var ledgerList = arrayListOf<BuyerLedgerUtils>()
    private var brokerLedgerList = arrayListOf<BuyerLedgerUtils>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_ledger)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.enterTransition = null
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_ledger)


        val permissions:Array<String> = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, Config.PICK_IMAGE_REQUEST_CODE)
        }

        ledger = intent?.getSerializableExtra(Config.LEDGER) as LedgerUtils
        isBroker = intent?.getBooleanExtra(Config.BROKER_FLAG, false)!!
        setUpToolbar()

        binding.makeTransaction.setOnClickListener {
            val intent = Intent(this, Transaction::class.java)
            intent.putExtra(Config.LEDGER, ledger)
            intent.putExtra(Config.OPERATION_FLAG, false)
            startActivity(intent)
            overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft)
        }

        binding.brokerTransaction.setOnClickListener {
            val intent = Intent(this, Transaction::class.java)
            intent.putExtra(Config.LEDGER, ledger)
            intent.putExtra(Config.OPERATION_FLAG, true)
            startActivity(intent)
            overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft)
        }

        binding.invoice.setOnClickListener {
            if (ledger.invoicePath.isNullOrEmpty()){
                Toast.makeText(
                    this,
                    "Not found!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val invoiceBuilder = InvoiceBuilder(this)
                invoiceBuilder.printPDF(ledger.invoicePath!!)
            }
        }


        binding.addInvoice.setOnClickListener {
            var intent = Intent(this, CreateInvoice::class.java)
            intent.putExtra(Config.LEDGER, ledger)
            intent.putExtra(Config.STAKEHOLDER, stakeholder)
            startActivity(intent)
            overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft)
        }

    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    private fun fetchData(){

        val ledgerPresenter = LedgerPresenterIml(this, this)
        ledgerPresenter.fetchLedgerById(ledger.ledgerId!!)

        ledgerPresenter.fetchSoldPacketListByLedgerId(ledger.ledgerId!!)

        val presenter = BuyerLedgerPresenterIml(this, this)
        presenter.fetchBuyerLedger(ledger.id!!, true)
        presenter.fetchBuyerLedger(ledger.id!!, false)
    }

    override fun onFetchLedgerListener(ledger: List<BuyerLedgerUtils>) {
        super.onFetchLedgerListener(ledger)
        ledgerList = ledger as ArrayList<BuyerLedgerUtils>
        if (ledgerList.size == 0)
            binding.ledger.visibility = View.GONE
        else
            binding.ledger.visibility = View.VISIBLE

        val adapter = BuyerLedgerListAdapter(
            this,
            ledgerList,
            this
        )

        binding.ledgerList.adapter = adapter
        binding.ledgerList.layoutManager = LinearLayoutManager(this)
        binding.ledgerList.setItemViewCacheSize(Config.LIST_CACHED_SIZE)
    }

    override fun onFetchBrokerLedgerListener(brokerLedger: List<BuyerLedgerUtils>) {
        super.onFetchBrokerLedgerListener(brokerLedger)
        brokerLedgerList = brokerLedger as ArrayList<BuyerLedgerUtils>
        if (brokerLedgerList.size == 0)
            binding.brokerLedger.visibility = View.GONE
        else
            binding.brokerLedger.visibility = View.VISIBLE

        Log.d(TAG, "BrokerLedgerList: $brokerLedgerList")
        val adapter = BuyerLedgerListAdapter(
            this,
            brokerLedgerList,
            this
        )

        binding.brokerLedgerList.adapter = adapter
        binding.brokerLedgerList.layoutManager = LinearLayoutManager(this)
        binding.brokerLedgerList.setItemViewCacheSize(Config.LIST_CACHED_SIZE)
    }

    override fun onLedgerDeleteClickListener(position: Int, type: Boolean) {
        super.onLedgerDeleteClickListener(position, type)
        Log.d(TAG, "Deleting")

        val builder = AlertDialog.Builder(this)

        builder.setTitle(Config.DELETE_LEDGER_TITLE)
        builder.setMessage(Config.DELETE_LEDGER_MESSAGE)

        builder.setIcon(R.drawable.ic_launcher)

        builder.setPositiveButton(
            Config.YES_MESSAGE
        ){
                dialog: DialogInterface?, which: Int ->

            val presenter = BuyerLedgerPresenterIml(this, this)
            if (type) {
                if (brokerLedgerList[position].transactionType == Config.CREDIT) {
                    ledger.brokerAmountPaid -= brokerLedgerList[position].amount
                    ledger.brokerAmountRemaining += brokerLedgerList[position].amount
                }else {
                    ledger.brokerAmountPaid += brokerLedgerList[position].amount
                    ledger.brokerAmountRemaining -= brokerLedgerList[position].amount
                }
                presenter.deleteBuyerLedger(ledger, brokerLedgerList[position])
            } else {
                if (ledgerList[position].transactionType == Config.CREDIT) {
                    ledger.paidAmount -= ledgerList[position].amount
                } else {
                    ledger.paidAmount += ledgerList[position].amount
                }

                presenter.deleteBuyerLedger(ledger, ledgerList[position])
            }
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
            Log.d(TAG, "Deleted:")
            fetchData()
        }
    }

    override fun onFetchLedger(ledger: LedgerUtils) {
        super.onFetchLedger(ledger)
        Log.d(TAG, "Ledger: $ledger")
        this.ledger = ledger
        val presenter = StakeholderPresenterIml(this, this)
        presenter.fetchStakeholderById(ledger.stakeHolderId)
        setViews()
    }

    override fun onFetchStakeholderListener(stakeholder: StakeholderUtils) {
        super.onFetchStakeholderListener(stakeholder)
        this.stakeholder = stakeholder
        Log.d(TAG, "StakeHolder: ${this.stakeholder}")
        var stakeholderName = if (stakeholder.type == Config.TYPE_ID_BUYER) {
            "Buyer: " + stakeholder.name
        } else {
            "Seller: " + stakeholder.name
        }
        binding.stakeHolderName.text = stakeholderName
    }

    override fun onFetchSoldPacketListListener(soldPacketList: List<SoldPacketUtils>) {
        super.onFetchSoldPacketListListener(soldPacketList)
        val adapter = SoldPacketAdapter(this, soldPacketList as ArrayList<SoldPacketUtils>)
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.setItemViewCacheSize(Config.LIST_CACHED_SIZE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Config.PICK_IMAGE_REQUEST_CODE && grantResults.isNotEmpty()) {
            onResume()
        }
    }
    private fun setViews() {

        if (isBroker)
            binding.makeTransaction.visibility = View.GONE

        if (ledger.imageUrl.isNullOrEmpty())
            binding.image.visibility = View.GONE
        else {
            binding.image.setImageURI(Uri.parse(ledger.imageUrl))
        }
        binding.ledgerId.text = ledger.ledgerId
        binding.paymentType.text = "Payment type: " + ledger.paymentType
        binding.brokerName.text = "Broker: " + ledger.brokerName

        binding.brokerPercentage.text = "Broker percentage: " + ledger.brokerPercentage.toString() + "%"
        binding.brokerAmount.text = "Broker amount: " + Config.RUPEE_SIGN + " " + ledger.brokerAmount.toString()

        binding.brokerAmountPaid.text = "Paid broker amount: " + Config.RUPEE_SIGN + " " + ledger.brokerAmountPaid.toString()
        binding.brokerAmountRemaining.text = "Due broker amount: " + Config.RUPEE_SIGN + " " + ledger.brokerAmountRemaining

        binding.discountPercentage.text = "Discount percentage: " + ledger.discountPercentage.toString() + "%"
        binding.discountAmount.text = "Discount amount: " + Config.RUPEE_SIGN + " " + ledger.discountAmount.toString()

        binding.totalWeight.text = "Total weight: " + ledger.totalWeight.toString() + " " + Config.CTS
        binding.totalPackets.text = "Total packets: " + ledger.totalPackets.toString()

        binding.totalAmount.text = "Total amount: " + Config.RUPEE_SIGN + " " + ledger.totalAmount.toString()
        binding.paidAmount.text = "Paid: " + Config.RUPEE_SIGN + " "  + ledger.paidAmount.toString()

        binding.brokerage.text = "Brokerage: " + Config.RUPEE_SIGN + " "  + ledger.brokerageAmount.toString()
        binding.due.text = "Due: " + Config.RUPEE_SIGN + " " + (ledger.totalAmount - ledger.paidAmount).toString()

        binding.date.text = "Date: " + ledger.date
        binding.dueDate.text = "Due date: " + ledger.dueDate

        binding.remark.text = ledger.remark
    }

    // Toolbar menu
    private fun setUpToolbar() {

        binding.toolbar.title = ledger.ledgerId

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