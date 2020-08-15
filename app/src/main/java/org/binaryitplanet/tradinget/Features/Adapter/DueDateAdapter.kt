package org.binaryitplanet.tradinget.Features.Adapter

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_due_dates_list.view.*
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.LedgerUtils

class DueDateAdapter(
    val context: Context,
    val ledgerList: ArrayList<LedgerUtils>
): RecyclerView.Adapter<DueDateAdapter.ViewHolder>() {
    private val TAG = "DueDateAdapter"
    // Holding the view

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(
                    R.layout.view_due_dates_list,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int = ledgerList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var view = holder.itemView

        view.buyerName.text = "Buyer name: " + ledgerList[position].stakeHolderName

        view.dueAmount.text = "Due amount: ${Config.RUPEE_SIGN} " +
                (ledgerList[position].totalAmount - ledgerList[position].paidAmount)

        view.call.setOnClickListener {
            makeCall(ledgerList[position].mobileNumber)
        }
    }

    private fun makeCall(number:String) {
        Log.d(TAG, "Making call to $number")
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$number")
        if(ContextCompat.checkSelfPermission(context,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context as Activity,
                arrayOf(Manifest.permission.CALL_PHONE), Config.REQUEST_CALL)
        } else {
            context.startActivity(intent)
        }
    }
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {}
}