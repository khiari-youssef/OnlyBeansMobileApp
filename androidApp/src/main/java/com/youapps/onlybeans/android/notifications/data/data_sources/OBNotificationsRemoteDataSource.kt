package com.youapps.onlybeans.android.notifications.data.data_sources

import com.youapps.onlybeans.android.notifications.data.dto.OBNotificationItemDataDTO
import com.youapps.onlybeans.android.notifications.domain.entities.OBNotificationItemData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update


interface OBNotificationsRemoteDataSource {

    fun fetchNotificationsByPagination(skip : Int,limit : Int) : Flow<List<OBNotificationItemData>>

    suspend fun updateNotificationReadStatus(id : String,isRead : Boolean) : Boolean

    suspend fun removeNotification(id : String) : Boolean

    suspend fun setAllCurrentNotificationsRead() : Boolean

    suspend fun fetchUnReadNotificationsCount() : Long
}


class OBNotificationsRemoteDataSourceImpl : OBNotificationsRemoteDataSource {

    var mockNotificationsState = MutableStateFlow<MutableList<OBNotificationItemDataDTO>>(
        mutableListOf()
    )


    override fun fetchNotificationsByPagination(
        skip: Int,
        limit: Int
    ): Flow<List<OBNotificationItemData>> = mockNotificationsState
        .map { mockNotifications->
            delay(1000)
            mockNotifications
                .drop(skip)
                .take(limit).mapNotNull {
                    it.toDomainModel()
                }
        }





    override suspend fun updateNotificationReadStatus(
        id: String,
        isRead: Boolean
    ): Boolean {
        mockNotificationsState.update { notifications ->
            notifications.map {
                if (it.id == id) {
                    it.copy(isRead = isRead)
                } else {
                    it
                }
            }.toMutableList()
        }
        return true
    }

    override suspend fun removeNotification(id: String): Boolean {
        mockNotificationsState.update { notifications->
           val isRemoved = notifications.removeIf {
                it.id == id
            }
            notifications
        }
        return true
        }

    override suspend fun setAllCurrentNotificationsRead(): Boolean {
        mockNotificationsState.update { notifications ->
            notifications.map {
                    it.copy(isRead = true)
            }.toMutableList()
        }
        return true
    }

    override suspend fun fetchUnReadNotificationsCount(): Long = mockNotificationsState.value.count {
        it.isRead.not()
    }.toLong()

}