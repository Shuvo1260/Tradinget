package org.binaryitplanet.tradinget.Features.Prsenter

import org.binaryitplanet.tradinget.Utils.StakeholderUtils

interface StakeholderPresenter {

    fun insertStakeholder(stakeholder: StakeholderUtils)
    fun deleteStakeholder(stakeholder: StakeholderUtils)
    fun updateStakeholder(stakeholder: StakeholderUtils)
    fun fetchStakeholder(type: Int)
    fun fetchStakeholderById(id: Long)

}