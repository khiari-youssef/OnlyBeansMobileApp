package com.youapps.onlybeans.data.dataSources

import com.youapps.onlybeans.data.dto.OBAddressDTO
import com.youapps.onlybeans.data.dto.OBCoffeeBeansPricingDTO
import com.youapps.onlybeans.data.dto.OBCoffeeBeansProductDetailsDTO
import com.youapps.onlybeans.data.dto.OBCoffeeRegionDTO
import com.youapps.onlybeans.data.dto.OBCoffeeRoasterDTO
import com.youapps.onlybeans.data.dto.OBCoffeeSpaceDTO
import com.youapps.onlybeans.data.dto.OBFileDTO
import com.youapps.onlybeans.data.dto.OBFlavorNotesDTO
import com.youapps.onlybeans.data.dto.OBHomeCoffeeBarDTO
import com.youapps.onlybeans.data.dto.OBHomeCoffeeBarID
import com.youapps.onlybeans.data.dto.OBLocationDTO
import com.youapps.onlybeans.data.dto.OBLoginResponseWrapper
import com.youapps.onlybeans.data.dto.OBProductListItemDTO
import com.youapps.onlybeans.data.dto.OBUserProfileDTO
import com.youapps.onlybeans.data.exceptions.CustomHttpException
import com.youapps.onlybeans.data.exceptions.HttpErrorType
import com.youapps.onlybeans.domain.entities.products.OBRoastLevel
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

internal class UsersRemoteDAO(
    private val restClient : HttpClient
) {



    private val tokenLogins : Map<String,String> = mapOf(
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InlvdXNzZWYua2hpYXJpQHNlc2FtZS5jb20udG4iLCJwYXNzd29yZCI6IjAwNzAwNyJ9.6a4KlE6CaP6NUyA1zDhPgHzQ7irJS5Y3MNw-RCEqzSM" to "khiari.youssef98@gmail.com",
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImFtaXJhQHNlc2FtZS5jb20udG4iLCJwYXNzd29yZCI6IjExMTEifQ.tTN0z-dKcWXRtzheCKDSCv6zn5yjwNdvKGDoqddeTXI" to "kais@gmail.com"
    )

    private val _mockUser = OBUserProfileDTO(
        email = "khiari.youssef98@gmail.com",
        firstName = "Youssef",
        secondName = "",
        sex = "m",
        status = "",
        nationality = "Tunisian",
        address = OBAddressDTO(
            country = "Tunisia",
            city = "Tunis"
        ),
        phone = "",
        profileDescription = "aaaaaaaa".repeat(12).repeat(4),
        coverPicture = "https://images.unsplash.com/photo-1601813913455-118810e79277?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1170",
        profilePicture = "https://avatar.iran.liara.run/public",
        keywords = listOf("Espresso","Nomad barista","Outin nano","Drip coffee","Colombian coffee"),
        myCoffeeSpace = OBCoffeeSpaceDTO(
              id = OBHomeCoffeeBarID,
            data = OBHomeCoffeeBarDTO(
                spaceId = "OBHomeCoffeeBarID",
                userEmail = "khiari.youssef98@gmail.com",
                description = "",
                gallery = listOf(
                    OBFileDTO(
                        uri = "https://images.unsplash.com/photo-1572982270699-473dfa34d7e7?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1170" ,
                        format = "image/png"
                    ),
                    OBFileDTO(
                        uri =  "https://images.unsplash.com/photo-1610889556528-9a770e32642f?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1315",
                        format = "image/png"
                    ),
                    OBFileDTO(
                        uri = "https://images.unsplash.com/photo-1522126039546-182129aa0b93?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1331",
                        format = "image/png"
                    ),
                    OBFileDTO(
                        uri =  "https://media.geeksforgeeks.org/wp-content/uploads/20201217163353/Screenrecorder-2020-12-17-16-32-03-350.mp4",
                        format = "video/mp4"
                    ),
                    OBFileDTO(
                        uri =  "https://images.unsplash.com/photo-1581068106019-5aa70c6ab424?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1171",
                        format ="image/png"
                    )
                ),
                coffeeGear = List(4){ id->
                    OBProductListItemDTO(
                        productID = "productID$id",
                        productName = "productName$id",
                        productDescription = "productName$id".repeat(6),
                        productImagePreview = "https://www.lamarzocco.com/fr/wp-content/uploads/2024/01/Linea-Mini-Rossa-front.png"
                    )
                },
                coffeeBeans = List(4){ id->
                    OBProductListItemDTO(
                        productID = "",
                        productName = "productName$id",
                        productDescription = "productName$id".repeat(6),
                        productImagePreview = ""
                    )
                   /*
                    OBCoffeeBeansProductDetailsDTO(
                        id = "ob-product-beans-123e4567-e89b-12d3-a456-426614174000",
                        label = "Café Quindio Milk Chocolate (Grano)",
                        productCovers = listOf(
                            "https://www.cafequindio.com.co/cdn/shop/files/cafe_cosecha_especial_chocolate_Frente.webp?v=1767389464&width=800"
                        ),
                        productDescription = "In the heart of Colombia's Coffee Region, in the picturesque area of \u200B\u200BCórdoba, Quindío, an exceptional coffee is cultivated that reflects the richness of its soil and the dedication of its growers. This coffee comes from beautiful farms where a small area is carefully selected and subjected to a special treatment that brings out its best qualities.",
                        species = "Arabica",
                        variety = "Castillo",
                        origins = listOf(OBCoffeeRegionDTO(
                            country = "Colombia",
                            flag = "ET",
                            region = "Córdoba, Quindío",
                            farm = "Various smallholders"
                        )),
                        processingMethod = "Washed",
                        flavorNotes = listOf(
                            OBFlavorNotesDTO("Lemon", "https://example.com/lemon.png"),
                            OBFlavorNotesDTO("Jasmine", "https://example.com/jasmine.png"),
                            OBFlavorNotesDTO("Black Tea", "https://example.com/tea.png")
                        ),
                        roastLevel = OBRoastLevel.MEDIUM.name,
                        roaster = OBCoffeeRoasterDTO(
                            id = "roaster-$id",
                            name = "Café Quindio"
                        ),
                        roastDate = "2025-12-29",
                        pricing = OBCoffeeBeansPricingDTO(
                            pricePerWeight = mapOf(
                                250f to 55,
                                500f to 100,
                                1000f to 200
                            ),
                            currency = "USD",
                            weightUnit = "g"
                        ),
                        endConsumptionDate = "2026-12-31"
                    )
                    */
                }
            )
        )
    )



    suspend fun fetchEmailAndPasswordLoginAPI(
        email : String,password : String
    ) : OBLoginResponseWrapper = withContext(Dispatchers.IO){
         delay(500)
       if (email == "khiari.youssef98@gmail.com" && password == "0000") OBLoginResponseWrapper(
           data = _mockUser,
           token = tokenLogins.keys.first()
       ) else throw CustomHttpException(errorType = HttpErrorType.UnauthorizedAccess)

    }

    suspend fun fetchTokenLoginAPI(
       token : String
    ) : OBLoginResponseWrapper  = withContext(Dispatchers.IO) {
        delay(300)

        if (token == tokenLogins.keys.first()) OBLoginResponseWrapper(
            data = _mockUser ,
            token = token
        ) else throw CustomHttpException(errorType = HttpErrorType.UnauthorizedAccess)
    }

    suspend fun fetchUserProfileData(token : String) : OBUserProfileDTO {
        delay(300)
        return  _mockUser
    }

}
