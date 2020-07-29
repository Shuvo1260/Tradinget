package org.binaryitplanet.tradinget.Features.Common

import org.binaryitplanet.tradinget.Utils.StakeholderUtils

interface StakeholderView {

    fun onSaveStakeholderListener(status: Boolean){}
    fun onUpdateStakeholderListener(status: Boolean){}
    fun onDeleteStakeholderListener(status: Boolean){}
    fun onFetchStakeholderListListener(stakeholderList: List<StakeholderUtils>){}
    fun onFetchStakeholderListener(stakeholder: StakeholderUtils){}

}