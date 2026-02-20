package com.youapps.onlybeans.data.dataSources

import com.youapps.onlybeans.OnlyBeansDatabase
import com.youapps.onlybeans.domain.entities.users.OBLocation
import io.ktor.client.HttpClient
import io.ktor.http.ContentDisposition.Companion.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext




data class OBCountry(
    val countryCode : String,
    val phonePrefix : String,
    val countryName : String,
    val countryFlag : String,
    val latLngBounds : List<OBLocation>?=null
)

class AppMetaDataSource(
    private val onlyBeansDatabase : OnlyBeansDatabase,
    private val restClient : HttpClient
) {




suspend fun getCountriesList(limit: Int,offset: Int) : List<OBCountry> = withContext(Dispatchers.IO){
       onlyBeansDatabase.onlyBeansDatabaseQueries.getCountries(
           limitparam = limit.toLong(),
           offsetparam = offset.toLong()
       ).executeAsList().map { db->
        OBCountry(
            countryCode = db.countryCode,
            phonePrefix = db.phonePrefix,
            countryName = db.countryName,
            countryFlag = db.countryFlag,
            latLngBounds = db.latLngBounds?.split(",")?.mapNotNull {
                OBLocation.fromString(it)
            }
        )
    }
}


suspend fun getCountryByCode(countryCode: String) : OBCountry? = withContext(Dispatchers.IO) {
    onlyBeansDatabase.onlyBeansDatabaseQueries.getCountriesByCode(
        countryCode = countryCode
    ).executeAsOneOrNull()?.let { (countryCode, phonePrefix, countryName, countryFlag) ->
        OBCountry(
            countryCode = countryCode,
            phonePrefix = phonePrefix,
            countryName = countryName,
            countryFlag = countryFlag,
            latLngBounds = listOf()
        )
    }
}


suspend fun setCountriesList(list:  List<OBCountry> ) = withContext(Dispatchers.IO){
        onlyBeansDatabase.onlyBeansDatabaseQueries.transactionWithResult{
            return@transactionWithResult list.forEach {
                onlyBeansDatabase.onlyBeansDatabaseQueries.insertCountries(
                    countryCode = it.countryCode,
                    phonePrefix = it.phonePrefix,
                    countryName = it.countryName,
                    countryFlag = it.countryFlag,
                    latLngBounds = it.latLngBounds?.joinToString(",")
                )
            }
        }
    }



}