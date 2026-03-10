package com.youapps.onlybeans.domain.entities.products


data class OBPrice(
    val price: Float,
    val discount: Float? = null
) {
    val netPrice = price * (1 - (discount ?: 0f))

    val getDiscountPercentage = discount?.times(100)?.toInt().toString()

}

sealed class OBProductPricing(
    open val currency: String
) {

    data class OBProductSinglePricing(
        val price: OBPrice,
        override val currency: String
    ) : OBProductPricing(currency)

    data class OBProductMultipleWeightBasedPricing(
        val pricePerWeight: Map<Int, OBPrice>,
        override val currency: String,
        val weightUnit: String
    ) : OBProductPricing(currency)

    data class OBProductMultipleBundleBasedPricing(
        val pricePerBundle: Map<String, OBPrice>,
        val discount: Float,
        override val currency: String
    ) : OBProductPricing(currency)
}


abstract class OBProduct(
    val id: String,
    val categoryID: String,
    val name: String,
    val displayMetadata: String,
    val productCovers: List<String>,
    val productDescription: String
)

data class OBMarketPlaceProduct(
    val marketPlaceID: String,
    val product: OBProduct,
    val pricing: OBProductPricing,
    val inStockItems: Int,
    val isAddedToFavoriteList: Boolean = false,
    val isAddedToCard: Boolean = false,
    val rating: OBProductRating? = null
)


data class OBMarketPlaceCardProduct(
    val productID: String,
    val productName: String,
    val productImagePreview : String?,
    val productCardDescription : String,
    val productQuantity : Int,
    val selectedPrice: OBPrice
) {
    override fun equals(other: Any?): Boolean =
        other is OBMarketPlaceCardProduct &&
        other.productID == this.productID

    override fun hashCode(): Int {
        var result = productQuantity
        result = 31 * result + productID.hashCode()
        result = 31 * result + productName.hashCode()
        result = 31 * result + (productImagePreview?.hashCode() ?: 0)
        result = 31 * result + productCardDescription.hashCode()
        result = 31 * result + selectedPrice.hashCode()
        return result
    }
}


