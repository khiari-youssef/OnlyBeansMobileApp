package com.youapps.onlybeans.domain.entities.products


data class OBPrice(
    val price : Float,
    val discount : Float?=null
){
    val netPrice = price * (1 - (discount ?: 0f))

    val getDiscountPercentage = discount?.times(100)?.toInt().toString()

}

sealed class OBProductPricing(
    open val currency : String
) {

    data class OBProductSinglePricing(
        val price : OBPrice,
        override val currency : String
    ) : OBProductPricing(currency)

    data class OBProductMultipleWeightBasedPricing(
        val pricePerWeight : Map<Int,OBPrice>,
        override val currency : String,
        val weightUnit : String
    ) : OBProductPricing(currency)

    data class OBProductMultipleBundleBasedPricing(
        val pricePerBundle : Map<String,OBPrice>,
        val discount : Float,
        override val currency : String
    ) : OBProductPricing(currency)
}


abstract class OBProduct(
    val id : String,
    val name : String,
    val displayMetadata : String,
    val productCovers : List<String>,
    val productDescription : String,
    val rating : OBProductRating?=null
)

data class OBMarketPlaceProduct(
    val marketPlaceID : String,
    val product : OBProduct,
    val pricing : OBProductPricing,
    val inStockItems: Int,
    val isAddedToFavoriteList : Boolean
)


