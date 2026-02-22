package com.youapps.search_module.search_list_map.ui.community_search_screen.map_view

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.youapps.search_module.search_list_map.domain.entities.MapSearchDataPoint


data class DataClusterItem(
    val data: MapSearchDataPoint,
    val itemSnippet: String
) : ClusterItem {
    override fun getPosition(): LatLng = LatLng(data.location.latitude, data.location.longitude)
    override fun getTitle(): String = data.title
    override fun getSnippet(): String = itemSnippet
    override fun getZIndex(): Float? = null
}