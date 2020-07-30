package org.binaryitplanet.tradinget.Features.Adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_list_item_stakeholder.view.*
import org.binaryitplanet.tradinget.Features.View.Broker.ViewBroker
import org.binaryitplanet.tradinget.Features.View.Buyer.ViewBuyer
import org.binaryitplanet.tradinget.Features.View.Seller.ViewSeller
import org.binaryitplanet.tradinget.R
import org.binaryitplanet.tradinget.Utils.Config
import org.binaryitplanet.tradinget.Utils.StakeholderUtils
import android.util.Pair as UtilPair

class StakeholderAdapter(
    val context: Context,
    val stakeholderList: ArrayList<StakeholderUtils>
): RecyclerView.Adapter<StakeholderAdapter.ViewHolder>() {
    private val TAG = "StakeholderAdapter"
    // Holding the view

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(
                    R.layout.view_list_item_stakeholder,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int = stakeholderList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var view = holder.itemView

        view.name.text = stakeholderList[position].name
        if (stakeholderList[position].firmName.isNullOrEmpty())
            view.firmName.visibility = View.GONE
        else
            view.firmName.text = stakeholderList[position].firmName
        view.mobileNumber.text = stakeholderList[position].mobileNumber
        view.altMobileNumber.text = stakeholderList[position].altMobileNumber



        view.setOnClickListener {
            Log.d(TAG, "StakeholderClicked: $position")

            var intent: Intent? = null

            if (stakeholderList[position].type == Config.TYPE_ID_BUYER) {
                intent = Intent(context, ViewBuyer::class.java)
            } else if (stakeholderList[position].type == Config.TYPE_ID_SELLER) {
                intent = Intent(context, ViewSeller::class.java)
            } else {
                intent = Intent(context, ViewBroker::class.java)
            }

            // Passing selected item data
            intent!!.putExtra(Config.STAKEHOLDER, stakeholderList[position])

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // Creating animation options
                val options =
                    ActivityOptions
                        .makeSceneTransitionAnimation(
                            context as Activity,
                            UtilPair.create(
                                view.name,
                                context.resources.getString(R.string.nameTransition)
                            ),
                            UtilPair.create(
                                view.mobileNumber,
                                context.resources.getString(R.string.mobileNumberTransition)
                            ),
                            UtilPair.create(
                                view.altMobileNumber,
                                context.resources.getString(R.string.altMobileNumberTransition)
                            )
                        )
                context.window.exitTransition = null
                context.startActivity(intent, options.toBundle())
            } else {
                context.startActivity(intent)
            }
        }
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {}
}