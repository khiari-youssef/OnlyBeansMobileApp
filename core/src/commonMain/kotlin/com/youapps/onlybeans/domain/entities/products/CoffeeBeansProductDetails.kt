package com.youapps.onlybeans.domain.entities.products

import com.youapps.onlybeans.data.dataSources.OBCountry
import com.youapps.onlybeans.domain.entities.users.OBLocation

data class OBCoffeeRoaster(
    val id : String,
    val name : String,
    val description: String?=null,
    val locations : List<OBLocation>?=null
)

data class OBCoffeeRegion(
    val country : String,
    val flag : String,
    val region : String?=null,
    val farm : String?=null,
    val description : String?=null
)

data class OBCoffeeBeansPricing(
    val pricePerWeight : Map<Float,Int>,
    val currency : String,
    val weightUnit : String
)

enum class OBRoastLevel{
    LIGHT,MEDIUM,DARK
}

data class OBFlavorNotes(
    val name : String,
    val thumbnailUrl : String?=null
)

data class OBFlavorProfileData(
    val description : String,
    val radarChartData : Map<String,Float>
)



data class OBCoffeeBeansProductDetails(
    val id : String,
    val label : String,
    val productCovers : List<String>,
    val productDescription : String,
    val species : String,
    val variety : String,
    val origins : List<OBCoffeeRegion>?=null,
    val altitude : String?=null,
    val processingMethod : String?=null,
    val flavorProfileData : OBFlavorProfileData?=null,
    val flavorNotes : List<OBFlavorNotes>,
    val roastLevel : OBRoastLevel,
    val roaster : OBCoffeeRoaster,
    val roastDate : String,
    val pricing : OBCoffeeBeansPricing,
    val endConsumptionDate : String,
    val rating : OBProductRating?=null,
){
    fun isSingleOrigin() : Boolean = origins?.size == 1
}