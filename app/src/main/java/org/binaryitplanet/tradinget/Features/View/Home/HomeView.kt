package org.binaryitplanet.tradinget.Features.View.Home

import org.binaryitplanet.tradinget.Utils.LedgerUtils

interface HomeView {
    fun onHomeItemsFetchListner(
        totalPayment: Double,
        duePayment: Double,
        weight: Double,
        inventoryValue: Double,
        overDueDate: Int,
        underDueDate: Int,
        overDueAmount: Double,
        underDueAmount: Double,
        totalSellerAmount: Double,
        sellerDueAmount: Double
    ){}

    fun onDueDateListFetchLisnter(dueDateList: List<LedgerUtils>){}
}