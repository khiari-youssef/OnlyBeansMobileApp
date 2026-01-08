package com.youapps.onlybeans.domain.entities.products

import com.youapps.onlybeans.domain.valueobjects.OBFile




abstract  class OBCoffeeSpace(
    val spaceId : String,
    val userEmail : String,
    val description : String?,
    val gallery : List<OBFile>?,
    val coffeeGear : List<OBProductListItem>,
    val coffeeBeans : List<OBProductListItem>,
)


class OBHomeCoffeeBar(
    spaceId : String,
    userEmail : String,
    description : String?,
    gallery : List<OBFile>?,
     coffeeGear : List<OBProductListItem>,
     coffeeBeans : List<OBProductListItem>,
) : OBCoffeeSpace(spaceId, userEmail, description,gallery,coffeeGear,coffeeBeans)

class OBCoffeeShop(
    spaceId : String,
    userEmail : String,
    description : String?,
    gallery : List<OBFile>?,
    coffeeGear : List<OBProductListItem>,
    coffeeBeans : List<OBProductListItem>,
) : OBCoffeeSpace(spaceId, userEmail, description, gallery,coffeeGear,coffeeBeans) {

}

class OBCoffeeCompany(
    spaceId : String,
    userEmail : String,
    description : String?,
    gallery : List<OBFile>?,
    coffeeGear : List<OBProductListItem>,
    coffeeBeans : List<OBProductListItem>,
) : OBCoffeeSpace(spaceId, userEmail, description, gallery,coffeeGear,coffeeBeans)

class OBCoffeeFarm(
    spaceId : String,
    userEmail : String,
    description : String?,
    gallery : List<OBFile>?,
    coffeeGear : List<OBProductListItem>,
    coffeeBeans : List<OBProductListItem>,
) : OBCoffeeSpace(spaceId, userEmail, description, gallery,coffeeGear,coffeeBeans) {

}