package com.youapps.onlybeans.data.repositories

import android.content.Context
import com.youapps.onlybeans.R
import com.youapps.onlybeans.data.dataSources.AppMetaDataSource
import com.youapps.onlybeans.data.dataSources.OBCountry
import com.youapps.onlybeans.domain.entities.users.OBLocation
import com.youapps.onlybeans.utilities.KMPLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlin.collections.buildList


internal actual class AppMetaDataAPIImpl(
    private val applicationContext: Context,
    private val dataSource : AppMetaDataSource
) : AppMetaDataAPI {

     actual override suspend fun initAppData() {
          withContext(Dispatchers.IO) {
             val countryCodes: List<OBCountry> =   applicationContext.resources.openRawResource(R.raw.countries_meta_data)
                 .bufferedReader().useLines { lines ->
                 return@useLines lines.drop(1).toList()
             }.map {
                 val row = it.split(",")
                 OBCountry(
                     countryFlag = applicationContext.getString(R.string.countries_api_url, row[0]),
                     countryCode = row[0],
                     phonePrefix = row[1],
                     countryName = row[2],
                     latLngBounds = runCatching {
                         listOf(
                             OBLocation(row[3].toDouble(), row[4].toDouble()),
                             OBLocation(row[5].toDouble(), row[6].toDouble())
                         )
                     }.getOrNull()
                 )
             }
              dataSource.setCountriesList(countryCodes)
         }
    }

     actual override fun getCountriesList(limit: Int,offset: Int) : Flow<List<OBCountry>> = flow{
        val countries = dataSource.getCountriesList(limit = limit, offset = offset)
        emit(countries)
    }

     actual override suspend fun getCountryByCode(countryCode: String): OBCountry? =dataSource.getCountryByCode(countryCode = countryCode)
 }