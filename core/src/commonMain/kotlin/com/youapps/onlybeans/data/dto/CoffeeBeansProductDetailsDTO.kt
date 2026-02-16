package com.youapps.onlybeans.data.dto


import com.youapps.onlybeans.domain.entities.products.OBCoffeeBeansProductDetails
import com.youapps.onlybeans.domain.entities.products.OBCoffeeRegion
import com.youapps.onlybeans.domain.entities.products.OBCoffeeRoaster
import com.youapps.onlybeans.domain.entities.products.OBFlavorNotes
import com.youapps.onlybeans.domain.entities.products.OBFlavorProfileData
import com.youapps.onlybeans.domain.entities.products.OBPrice
import com.youapps.onlybeans.domain.entities.products.OBProductPricing
import com.youapps.onlybeans.domain.entities.products.OBProductRating
import com.youapps.onlybeans.domain.entities.products.OBRoastLevel
import kotlinx.serialization.Serializable

@Serializable
data class OBCoffeeRoasterDTO(
    val id : String,
    val name : String,
    val description: String?=null,
    val locations : List<OBLocationDTO>?=null
) {
    fun toDomain() : OBCoffeeRoaster = OBCoffeeRoaster(
        id = this.id,
        name = this.name,
        locations = this.locations?.map(OBLocationDTO::toDomainModel),
        description = this.description
    )
}

@Serializable
data class OBCoffeeRegionDTO(
    val country : String,
    val flag : String,
    val region : String?=null,
    val farm : String?=null,
    val description : String?=null
){
    fun toDomain() : OBCoffeeRegion = OBCoffeeRegion(
        country = this.country,
        flag = this.flag,
        region = this.region,
        farm = this.farm,
        description = this.description
    )
}

@Serializable
data class OBPriceDTO(
    val price : Float,
    val discount : Float
){
    fun toDomain() : OBPrice = OBPrice(
        price = this.price,
        discount = this.discount
    )
}

@Serializable
data class OBCoffeeBeansPricingDTO(
    val pricePerWeight : Map<Int,OBPriceDTO>,
    val currency : String,
    val weightUnit : String
){
    fun toDomain() : OBProductPricing.OBProductMultipleWeightBasedPricing = OBProductPricing.OBProductMultipleWeightBasedPricing(
        pricePerWeight = this.pricePerWeight.map { (weight,price) ->
            weight to OBPrice(
                price = price.price,
                discount = price.discount
            ) }.toMap(),
        currency = this.currency,
        weightUnit = this.weightUnit
    )
}

@Serializable
data class OBFlavorNotesDTO(
    val name : String,
    val thumbnailUrl : String
) {
    fun toDomain() : OBFlavorNotes = OBFlavorNotes(
        name = this.name,
        thumbnailUrl = this.thumbnailUrl
    )
}

@Serializable
data class OBFlavorProfileDataDTO(
    val description : String,
    val radarChartData : Map<String,Float>
){
    fun toDomain() : OBFlavorProfileData = OBFlavorProfileData(
        description = this.description,
        radarChartData = this.radarChartData
    )
}

@Serializable
data class OBCoffeeBeansProductDetailsDTO(
    val id : String,
    val name : String,
    val displayMetadata : String,
    val productCovers : List<String>,
    val productDescription : String,
    val species : String,
    val variety : String,
    val origins : List<OBCoffeeRegionDTO>?=null,
    val altitude : String?=null,
    val processingMethod : String?=null,
    val flavorProfileData : OBFlavorProfileDataDTO?=null,
    val flavorNotes : List<OBFlavorNotesDTO>,
    val roastLevel : String,
    val roaster : OBCoffeeRoasterDTO,
    val roastDate : String,
    val pricing : OBCoffeeBeansPricingDTO,
    val endConsumptionDate : String,
    val averageRating : Float?=null,
    val reviewsNumber : Int?=null,
) {
    fun toDomain() : OBCoffeeBeansProductDetails =OBCoffeeBeansProductDetails(
       id = this.id,
       name = this.name,
        displayMetadata = displayMetadata,
      productCovers = this.productCovers,
      productDescription = this.productDescription,
      species = this.species,
      variety = this.variety,
      origins = this.origins?.map { it.toDomain() },
      altitude = this.altitude,
      processingMethod = this.processingMethod,
      flavorProfileData = this.flavorProfileData?.toDomain(),
      flavorNotes = this.flavorNotes.map(OBFlavorNotesDTO::toDomain),
      roastDate = this.roastDate,
      roastLevel = OBRoastLevel.valueOf(this.roastLevel),
      roaster = this.roaster.toDomain(),
      endConsumptionDate = this.endConsumptionDate
    )
}