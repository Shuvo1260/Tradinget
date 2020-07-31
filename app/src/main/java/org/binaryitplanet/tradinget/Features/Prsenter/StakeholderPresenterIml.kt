package org.binaryitplanet.tradinget.Features.Prsenter

import android.content.Context
import android.util.Log
import org.binaryitplanet.tradinget.Features.Common.StakeholderView
import org.binaryitplanet.tradinget.Features.Model.DatabaseManager
import org.binaryitplanet.tradinget.Utils.StakeholderUtils
import java.lang.Exception

class StakeholderPresenterIml(
    private var context: Context,
    private var stakeholderView: StakeholderView
): StakeholderPresenter {

    private val TAG = "StakeholderPresenter"


    override fun insertStakeholder(stakeholder: StakeholderUtils) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val id = databaseManager.getStakeholderDAO().insert(stakeholder)

            if (id > 0)
                stakeholderView.onSaveStakeholderListener(true)
            else
                stakeholderView.onSaveStakeholderListener(false)
            Log.d(TAG, "Saving $id: $stakeholder")
        }catch (e: Exception){
            Log.d(TAG, "InsertStakeholderError: ${e.message}")
            stakeholderView.onSaveStakeholderListener(false)
        }
    }

    override fun deleteStakeholder(stakeholder: StakeholderUtils) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val id = databaseManager.getStakeholderDAO().delete(stakeholder)

            if (id > 0)
                stakeholderView.onDeleteStakeholderListener(true)
            else
                stakeholderView.onDeleteStakeholderListener(false)
            Log.d(TAG, "Deleting $id: $stakeholder")
        }catch (e: Exception){
            Log.d(TAG, "DeleteStakeholderError: ${e.message}")
            stakeholderView.onDeleteStakeholderListener(false)
        }
    }

    override fun updateStakeholder(stakeholder: StakeholderUtils) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val id = databaseManager.getStakeholderDAO().update(stakeholder)

            if (id > 0)
                stakeholderView.onUpdateStakeholderListener(true)
            else
                stakeholderView.onUpdateStakeholderListener(false)
            Log.d(TAG, "Updating $id: $stakeholder")
        }catch (e: Exception){
            Log.d(TAG, "UpdateStakeholderError: ${e.message}")
            stakeholderView.onUpdateStakeholderListener(false)
        }
    }

    override fun fetchStakeholder(type: Int) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val stakeholderList = databaseManager
                .getStakeholderDAO().getStakeholderByType(type)

            stakeholderView.onFetchStakeholderListListener(stakeholderList)
        }catch (e: Exception){
            Log.d(TAG, "FetchStakeholderListError: ${e.message}")
        }
    }

    override fun fetchStakeholderById(id: Long) {
        try {
            val databaseManager = DatabaseManager.getInstance(context)!!
            val stakeholder = databaseManager
                .getStakeholderDAO().getStakeholderById(id)

            stakeholderView.onFetchStakeholderListener(stakeholder)
        }catch (e: Exception){
            Log.d(TAG, "FetchStakeholderError: ${e.message}")
        }
    }
}