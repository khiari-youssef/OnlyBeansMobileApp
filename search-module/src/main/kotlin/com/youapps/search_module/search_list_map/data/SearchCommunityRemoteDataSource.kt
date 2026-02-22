package com.youapps.search_module.search_list_map.data

import com.youapps.onlybeans.domain.entities.users.OBLocation
import com.youapps.search_module.search_list_map.domain.entities.MapSearchDataPoint
import com.youapps.search_module.search_list_map.domain.entities.OBMapSearchQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


interface SearchCommunityRemoteDataSource {

    suspend fun fetchCommunityDataByCustomQuery(query: OBMapSearchQuery): List<MapSearchDataPoint>

}

class SearchCommunityRemoteDataSourceImpl : SearchCommunityRemoteDataSource {

    data class LatLng(val latitude: Double, val longitude: Double)

    val tunisiaLocations = listOf(
        LatLng(36.8065, 10.1815),  // Tunis (Capital)
        LatLng(37.2744, 9.8739),   // Bizerte (North)
        LatLng(36.8941, 10.1870),  // Ariana
        LatLng(36.7333, 10.2333),  // Ben Arous
        LatLng(36.4512, 10.7357),  // Nabeul (Cap Bon)
        LatLng(35.8256, 10.6084),  // Sousse (Sahel)
        LatLng(35.6781, 10.0963),  // Kairouan (Central)
        LatLng(35.5024, 11.0457),  // Mahdia
        LatLng(34.7400, 10.7600),  // Sfax
        LatLng(33.8814, 10.0982),  // Gabes
        LatLng(33.8075, 10.8451),  // Djerba (Houmt Souk)
        LatLng(33.5124, 11.0845),  // Zarzis
        LatLng(32.9211, 10.4509),  // Tataouine (Deep South)
        LatLng(33.9185, 8.1336),   // Tozeur (Oasis)
        LatLng(33.7043, 8.9690),   // Kebili
        LatLng(34.4311, 8.7756),   // Gafsa
        LatLng(35.1723, 8.8312),   // Kasserine
        LatLng(36.1678, 8.7012),   // El Kef (North West)
        LatLng(36.5011, 8.7794),   // Jendouba
        LatLng(36.7256, 9.1817),
        LatLng(48.8566, 2.3522),   // Paris
        LatLng(43.2965, 5.3698),   // Marseille
        LatLng(45.7640, 4.8357),   // Lyon
        LatLng(43.6047, 1.4442),   // Toulouse
        LatLng(43.7102, 7.2620),   // Nice
        LatLng(47.2184, -1.5536),  // Nantes
        LatLng(48.5734, 7.7521),   // Strasbourg
        LatLng(44.8378, -0.5792),  // Bordeaux
        LatLng(50.6292, 3.0573),   // Lille
        LatLng(43.6108, 3.8767),   // Montpellier
        LatLng(48.1173, -1.6778),  // Rennes
        LatLng(49.4432, 1.0999),   // Rouen
        LatLng(45.4408, 4.3859),   // Saint-Étienne
        LatLng(43.1242, 5.9280),   // Toulon
        LatLng(45.1885, 5.7245),   // Grenoble
        LatLng(47.3220, 5.0415),   // Dijon
        LatLng(49.2583, 4.0317),   // Reims
        LatLng(46.2276, 2.2137),   // Central France (Rural)
        LatLng(48.4000, -4.4833),  // Brest (Brittany)
        LatLng(42.6887, 8.9311),
        LatLng(4.7110, -74.0721),  // Bogotá (Highlands)
        LatLng(6.2442, -75.5812),  // Medellín (Andes)
        LatLng(3.4516, -76.5320),  // Cali
        LatLng(10.3910, -75.4794), // Cartagena (Caribbean)
        LatLng(10.9639, -74.7964), // Barranquilla
        LatLng(7.1193, -73.1227),  // Bucaramanga
        LatLng(11.2408, -74.1990), // Santa Marta
        LatLng(4.8133, -75.6961),  // Pereira (Coffee Region)
        LatLng(4.4389, -75.2322),  // Ibagué
        LatLng(1.2136, -77.2811),  // Pasto (South)
        LatLng(7.8939, -72.5078),  // Cúcuta (Border)
        LatLng(4.1420, -73.6266),  // Villavicencio (Plains)
        LatLng(2.4419, -76.6063),  // Popayán
        LatLng(0.6667, -76.5000),  // Mocoa (Amazon Gateway)
        LatLng(-4.2126, -69.9406), // Leticia (Deep Amazon)
        LatLng(5.6922, -76.6582),  // Quibdó (Pacific side)
        LatLng(12.5847, -81.7006), // San Andrés Island
        LatLng(8.7515, -75.8814),  // Montería
        LatLng(2.9273, -75.2819),  // Neiva
        LatLng(5.3333, -72.4000)   // Yopal// Béja
    )

    override suspend fun fetchCommunityDataByCustomQuery(query: OBMapSearchQuery): List<MapSearchDataPoint> =
        withContext(Dispatchers.IO) {
            tunisiaLocations.filter { latLng ->
                val isLatInRange = latLng.latitude >= query.mapBounds.southWest.latitude &&
                        latLng.latitude <= query.mapBounds.northEast.latitude
                val isLngInRange = latLng.longitude >= query.mapBounds.southWest.longitude &&
                        latLng.longitude <= query.mapBounds.northEast.longitude
                isLatInRange && isLngInRange
            }.mapIndexed { index, latLng ->
                MapSearchDataPoint(
                    location = OBLocation(
                        latitude = latLng.latitude,
                        longitude = latLng.longitude
                    ),
                    title = "location $index"
                )
            }
        }

}