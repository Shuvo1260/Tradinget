package org.binaryitplanet.tradinget.Features.Prsenter

import android.content.Context
import android.util.Log
import org.binaryitplanet.tradinget.Features.Model.DatabaseManager
import org.binaryitplanet.tradinget.Features.View.Inventory.ViewPacketDetails
import org.binaryitplanet.tradinget.Utils.PacketDetailsUtils
import org.binaryitplanet.tradinget.Utils.PacketUtils
import java.lang.Exception

class PacketDetailsPresenterIml(
    private var context: Context,
    private var viewPacketDetails: ViewPacketDetails
): PacketDetailsPresenter {

    private val TAG = "PacketDetailsPresenter"

    override fun insertPacketDetails(packet: PacketUtils, packetDetails: PacketDetailsUtils) {

        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val id = databaseManager.getPacketDetailsDAO().insert(packetDetails)

//            databaseManager.getPacketDAO().update(packet)

            if (id > 0)
                viewPacketDetails.onSavePacketDetailsListener(true)
            else
                viewPacketDetails.onSavePacketDetailsListener(false)
            Log.d(TAG, "Saving $id: $packetDetails")
        }catch (e: Exception){
            Log.d(TAG, "InsertPacketDetailsError: ${e.message}")
            viewPacketDetails.onSavePacketDetailsListener(false)
        }
    }

    override fun deletePacketDetails(packetDetails: PacketDetailsUtils) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val id = databaseManager.getPacketDetailsDAO().delete(packetDetails)

            if (id > 0)
                viewPacketDetails.onDeletePacketDetailsListener(true)
            else
                viewPacketDetails.onDeletePacketDetailsListener(false)
            Log.d(TAG, "Deleting $id: $packetDetails")
        }catch (e: Exception){
            Log.d(TAG, "DeletePacketDetailsError: ${e.message}")
            viewPacketDetails.onDeletePacketDetailsListener(false)
        }
    }

    override fun updatePacketDetails(packetDetails: PacketDetailsUtils) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val id = databaseManager.getPacketDetailsDAO().update(packetDetails)

            if (id > 0)
                viewPacketDetails.onUpdatePacketDetailsListener(true)
            else
                viewPacketDetails.onUpdatePacketDetailsListener(false)
            Log.d(TAG, "Updating $id: $packetDetails")
        }catch (e: Exception){
            Log.d(TAG, "UpdatePacketDetailsError: ${e.message}")
            viewPacketDetails.onUpdatePacketDetailsListener(false)
        }
    }

    override fun fetchPacketDetailsById(id: Long) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val packet = databaseManager
                .getPacketDetailsDAO().getPacketDetailsById(id)

            viewPacketDetails.onFetchPacketDetailsListener(packet)
        }catch (e: Exception){
            Log.d(TAG, "FetchPacketDetailsError: ${e.message}")
        }
    }

    override fun fetchPacketDetailsList(packetId: String) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val packetList = databaseManager
                .getPacketDetailsDAO().getPacketDetailsList(packetId)

            viewPacketDetails.onFetchPacketDetailsListListener(packetList)
        }catch (e: Exception){
            Log.d(TAG, "FetchPacketDetailsListError: ${e.message}")
        }
    }

    override fun fetchAllPacketDetailsList() {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val packetList = databaseManager
                .getPacketDetailsDAO().getAllPacketDetailsList()

            viewPacketDetails.onFetchAllPacketDetailsListListener(packetList)
        }catch (e: Exception){
            Log.d(TAG, "FetchAllPacketDetailsListError: ${e.message}")
        }
    }
}