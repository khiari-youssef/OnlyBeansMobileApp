package com.youapps.onlybeans.data.dto

import com.youapps.onlybeans.domain.entities.products.OBProductRating
import kotlinx.serialization.Serializable

@Serializable
data class OBProductDTO(
    val id : String,
    val name : String,
    val productCovers : List<String>,
    val productDescription : String,
    val averageRating : Float?=null,
    val reviewsNumber : Int?=null,
)