package org.binaryitplanet.tradinget.Features.Prsenter

import android.content.Context
import android.util.Log
import org.binaryitplanet.tradinget.Features.Model.DatabaseManager
import org.binaryitplanet.tradinget.Features.View.Inventory.InventoryView
import org.binaryitplanet.tradinget.Utils.PacketUtils
import java.lang.Exception

class PacketPresenterIml(
    private var context: Context,
    private var inventoryView: InventoryView
): PacketPresenter {

    private val TAG = "InventoryPresenter"
    
    override fun insertPacket(packet: PacketUtils) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val id = databaseManager.getPacketDAO().insert(packet)

            if (id > 0)
                inventoryView.onSavePacketListener(true)
            else
                inventoryView.onSavePacketListener(false)
            Log.d(TAG, "Saving $id: $packet")
        }catch (e: Exception){
            Log.d(TAG, "InsertPacketError: ${e.message}")
            inventoryView.onSavePacketListener(false)
        }
    }

    override fun deletePacket(packet: PacketUtils) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val id = databaseManager.getPacketDAO().delete(packet)

            if (id > 0)
                inventoryView.onDeletePacketListener(true)
            else
                inventoryView.onDeletePacketListener(false)
            Log.d(TAG, "Deleting $id: $packet")
        }catch (e: Exception){
            Log.d(TAG, "DeletePacketError: ${e.message}")
            inventoryView.onDeletePacketListener(false)
        }
    }

    override fun updatePacket(packet: PacketUtils) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val id = databaseManager.getPacketDAO().update(packet)

            if (id > 0)
                inventoryView.onUpdatePacketListener(true)
            else
                inventoryView.onUpdatePacketListener(false)
            Log.d(TAG, "Updating $id: $packet")
        }catch (e: Exception){
            Log.d(TAG, "UpdatePacketError: ${e.message}")
            inventoryView.onUpdatePacketListener(false)
        }
    }

    override fun fetchPacketById(id: Long) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val packet = databaseManager
                .getPacketDAO().getPacketById(id)

            inventoryView.onFetchPacketListener(packet)
        }catch (e: Exception){
            Log.d(TAG, "FetchPacketError: ${e.message}")
        }
    }

    override fun fetchPacketList() {

        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val packetList = databaseManager
                .getPacketDAO().getPacketList()

            inventoryView.onFetchPacketListListener(packetList)
        }catch (e: Exception){
            Log.d(TAG, "FetchPacketListError: ${e.message}")
        }
    }
}