package org.binaryitplanet.tradinget.Features.View.Invoice

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import org.binaryitplanet.tradinget.Features.Adapter.NoteListAdapter
import org.binaryitplanet.tradinget.Features.Prsenter.InvoicePresenterIml
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.InvoiceSettingsUtils
import org.binaryitplanet.tradinget.Utils.NotesUtils
import org.binaryitplanet.tradinget.databinding.ActivityInvoiceSettingsEditBinding

class InvoiceSettingsEdit : AppCompatActivity(), InvoiceSettingsView {
    private val TAG = "InvoiceSettingsEdit"
    private lateinit var binding: ActivityInvoiceSettingsEditBinding

    private var notesList = arrayListOf<NotesUtils>()

    private lateinit var name: String
    private lateinit var firmName: String
    private lateinit var mobileNumber: String
    private lateinit var altMobileNumber: String
    private lateinit var address1: String
    private lateinit var address2: String
    private lateinit var address3: String
    private lateinit var address4: String
    private lateinit var stateCode: String
    private lateinit var gstNumber: String
    private lateinit var panNumber: String
    private lateinit var bankNameAndBrunch: String
    private lateinit var currentAccount: String
    private lateinit var ifsc: String
    private lateinit var hsnNumber: String
    private var imageUrl: String? = null

    private var operationFlag = true

    private lateinit var invoiceSettingsUtils: InvoiceSettingsUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice_settings_edit)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_invoice_settings_edit)

        setUpToolbar()
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.done && checkValidity()) {
                if (operationFlag)
                    saveData()
                else
                    updateData()
            }
            return@setOnMenuItemClickListener super.onOptionsItemSelected(it)
        }


        binding.addNote.setOnClickListener {
            if (binding.note.text.isNullOrEmpty()) {
                binding.note.error = Config.REQUIRED_FIELD
                binding.note.requestFocus()
            } else {
                notesList.add(
                    NotesUtils(
                        null,
                        (notesList.size + 1).toString() + ". " + binding.note.text.toString()
                    )
                )

                setupNoteList()
            }
        }


        binding.addImage.setOnClickListener {

            val permissions:Array<String> = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions, Config.PICK_IMAGE_REQUEST_CODE)
            }
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Config.PICK_IMAGE_REQUEST_CODE && grantResults.isNotEmpty()) {
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
            imageUrl = imageUri.toString()
            Log.d(TAG, "ImagePath: $imageUrl")
            binding.addImage.setImageURI(imageUri)

        }
    }

    override fun onResume() {
        super.onResume()
        operationFlag = intent?.getBooleanExtra(Config.OPERATION_FLAG, true)!!

        if (!operationFlag) {
            val presenter = InvoicePresenterIml(this, this)
            presenter.fetchInvoiceSettings()
        }
    }

    override fun onFetchInvoiceSettingsListener(
        invoiceSEttings: InvoiceSettingsUtils,
        noteList: List<NotesUtils>
    ) {
        super.onFetchInvoiceSettingsListener(invoiceSEttings, noteList)
        invoiceSettingsUtils = invoiceSEttings
        notesList = noteList as ArrayList<NotesUtils>

        setViews()
        setupNoteList()
    }

    private fun setViews() {
        binding.name.setText(invoiceSettingsUtils.name)
        binding.firmName.setText(invoiceSettingsUtils.firmName)
        binding.mobileNumber.setText(invoiceSettingsUtils.mobileNumber)
        binding.altMobileNumber.setText(invoiceSettingsUtils.altMobileNumber)
        binding.address1.setText(invoiceSettingsUtils.address1)
        binding.address2.setText(invoiceSettingsUtils.address2)
        binding.address3.setText(invoiceSettingsUtils.address3)
        binding.address4.setText(invoiceSettingsUtils.address4)
        binding.stateCode.setText(invoiceSettingsUtils.stateCode)
        binding.panNumber.setText(invoiceSettingsUtils.panNumber)
        binding.gstNumber.setText(invoiceSettingsUtils.gstNumber)
        binding.bankName.setText(invoiceSettingsUtils.bankNameAndBrunch)
        binding.currentAccount.setText(invoiceSettingsUtils.bankAccount)
        binding.ifsc.setText(invoiceSettingsUtils.bankIFSC)
        binding.hsnNumber.setText(invoiceSettingsUtils.hsnNumber)

        if (!invoiceSettingsUtils.imageUrl.isNullOrEmpty()) {
            binding.addImage.setImageURI(Uri.parse(invoiceSettingsUtils.imageUrl))
        }
    }

    private fun updateData() {

        invoiceSettingsUtils.name = name
        invoiceSettingsUtils.firmName = firmName
        invoiceSettingsUtils.mobileNumber = mobileNumber
        invoiceSettingsUtils.altMobileNumber = altMobileNumber
        invoiceSettingsUtils.address1 = address1
        invoiceSettingsUtils.address2 = address2
        invoiceSettingsUtils.address3 = address3
        invoiceSettingsUtils.address4 = address4
        invoiceSettingsUtils.stateCode = stateCode
        invoiceSettingsUtils.gstNumber = gstNumber
        invoiceSettingsUtils.panNumber = panNumber
        invoiceSettingsUtils.bankNameAndBrunch = bankNameAndBrunch
        invoiceSettingsUtils.bankAccount = currentAccount
        invoiceSettingsUtils.bankIFSC = ifsc
        invoiceSettingsUtils.imageUrl = imageUrl!!
        invoiceSettingsUtils.hsnNumber = hsnNumber

        val presenter = InvoicePresenterIml(this, this)
        presenter.updateInvoiceSettings(
            invoiceSettingsUtils,
            notesList
        )
    }

    override fun onUpdateInvoiceSettingsListener(status: Boolean) {
        super.onUpdateInvoiceSettingsListener(status)
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

    private fun saveData() {
        invoiceSettingsUtils = InvoiceSettingsUtils(
            null,
            name,
            firmName,
            mobileNumber,
            altMobileNumber,
            address1,
            address2,
            address3,
            address4,
            stateCode,
            gstNumber,
            panNumber,
            bankNameAndBrunch,
            currentAccount,
            ifsc,
            hsnNumber,
            imageUrl

        )

        val presenter = InvoicePresenterIml(this, this)
        presenter.insertInvoiceSettings(
            invoiceSettingsUtils,
            notesList
        )
    }

    override fun onInsertInvoiceSettingsListener(status: Boolean) {
        super.onInsertInvoiceSettingsListener(status)
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
        name = binding.name.text.toString()
        firmName = binding.firmName.text.toString()
        mobileNumber = binding.mobileNumber.text.toString()
        altMobileNumber = binding.altMobileNumber.text.toString()
        address1 = binding.address1.text.toString()
        address2 = binding.address2.text.toString()
        address3 = binding.address3.text.toString()
        address4 = binding.address4.text.toString()
        stateCode = binding.stateCode.text.toString()
        gstNumber = binding.gstNumber.text.toString()
        panNumber = binding.panNumber.text.toString()
        bankNameAndBrunch = binding.bankName.text.toString()
        currentAccount = binding.currentAccount.text.toString()
        ifsc = binding.ifsc.text.toString()
        hsnNumber = binding.hsnNumber.text.toString()

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
        if (address1.isNullOrEmpty()) {
            binding.address1.error = Config.REQUIRED_FIELD
            binding.address1.requestFocus()
            return false
        }
        if (stateCode.isNullOrEmpty()) {
            binding.stateCode.error = Config.REQUIRED_FIELD
            binding.stateCode.requestFocus()
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
        if (hsnNumber.isNullOrEmpty()) {
            binding.hsnNumber.error = Config.REQUIRED_FIELD
            binding.hsnNumber.requestFocus()
            return false
        }
        if (bankNameAndBrunch.isNullOrEmpty()) {
            binding.bankName.error = Config.REQUIRED_FIELD
            binding.bankName.requestFocus()
            return false
        }
        if (currentAccount.isNullOrEmpty()) {
            binding.currentAccount.error = Config.REQUIRED_FIELD
            binding.currentAccount.requestFocus()
            return false
        }
        if (ifsc.isNullOrEmpty()) {
            binding.ifsc.error = Config.REQUIRED_FIELD
            binding.ifsc.requestFocus()
            return false
        }

        if (notesList.size <= 0) {
            binding.note.error = Config.REQUIRED_FIELD
            binding.note.requestFocus()
            return false
        }

        return true
    }

    private fun setupNoteList() {
        val adapter = NoteListAdapter(this, notesList)
        binding.notesList.adapter = adapter
        binding.notesList.layoutManager = LinearLayoutManager(this)
        binding.notesList.setItemViewCacheSize(Config.LIST_CACHED_SIZE)

        binding.note.setText("")
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