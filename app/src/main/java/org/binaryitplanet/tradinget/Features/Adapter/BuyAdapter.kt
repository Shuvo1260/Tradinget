package org.binaryitplanet.tradinget.Features.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_buy_list_item.view.*
import org.binaryitplanet.tradinget.Features.View.Ledger.ViewLedger
import org.binaryitplanet.tradinget.Features.View.Seller.ViewBuy
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.*
import android.util.Pair as UtilPair

class BuyAdapter(
    val context: Context,
    val buyList: ArrayList<BuyUtils>
): RecyclerView.Adapter<BuyAdapter.ViewHolder>() {
    private val TAG = "PacketAdapter"
    // Holding the view

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(
                    R.layout.view_buy_list_item,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int = buyList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var view = holder.itemView

        view.brokerName.text = "Broker name: " + getValue(buyList[position].brokerName)

        view.date.text = "Purchase date: " + buyList[position].purchaseDate

        var dueDays = (buyList[position].dueDateMilli - buyList[position].purchaseDateMilli) / (24 * 60 * 60000)

        view.dueDate.text = "Due days: $dueDays"

        view.weight.text = "Weight: " + buyList[position].weight.toString() + " " + Config.CTS
        view.rate.text = "Rate: " + Config.RUPEE_SIGN + " " + buyList[position].rate.toString()

        view.discountAmount.text = "Discount: " + Config.RUPEE_SIGN + " " + buyList[position].discountAmount.toString()
        view.amount.text = "Total: " + Config.RUPEE_SIGN + " " + buyList[position].amount.toString()



        view.setOnClickListener {
            Log.d(TAG, "PacketClicked: $position")

            var intent: Intent? = Intent(context, ViewBuy::class.java)
            // Passing selected item data
            intent!!.putExtra(Config.BUY, buyList[position])
            context.startActivity(intent)
        }
    }

    private fun getValue(brokerName: String?): String {
        return if (brokerName.isNullOrEmpty())
            "N.A."
        else
            brokerName
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {}
}