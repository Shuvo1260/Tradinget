package org.binaryitplanet.tradinget.Features.Prsenter

import org.binaryitplanet.tradinget.Utils.BuyUtils

interface BuyPresenter {
    fun insertBuy(buyUtils: BuyUtils)
    fun deleteBuy(buyUtils: BuyUtils)
    fun updateBuy(buyUtils: BuyUtils)
    fun fetchBuyListBySellerId(sellerId: Long)
    fun fetchBuyById(id: Long)
}