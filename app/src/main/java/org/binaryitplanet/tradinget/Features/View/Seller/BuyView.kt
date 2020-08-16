package org.binaryitplanet.tradinget.Features.View.Seller

import org.binaryitplanet.tradinget.Utils.BuyUtils

interface BuyView {
    fun insertBuyListener(status: Boolean){}
    fun deleteBuyListener(status: Boolean){}
    fun updateBuyListener(status: Boolean){}

    fun fetchBuyListListener(buyList: List<BuyUtils>){}
    fun fetchBuyListener(buy: BuyUtils){}
}