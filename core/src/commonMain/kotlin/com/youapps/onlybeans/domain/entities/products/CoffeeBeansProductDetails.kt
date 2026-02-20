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



 class OBCoffeeBeansProductDetails(
     id : String,
     name : String,
     displayMetadata : String,
     productCovers : List<String>,
     productDescription : String,
     categoryID : String,
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
    val endConsumptionDate : String
) : OBProduct(
    id = id,
    name = name,
    productCovers = productCovers,
    productDescription = productDescription,
    displayMetadata = displayMetadata,
    categoryID = categoryID
){
    fun isSingleOrigin() : Boolean = origins?.size == 1
 }