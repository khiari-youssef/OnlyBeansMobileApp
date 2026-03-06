package com.youapps.onlybeans.android.notifications.data.repositories


import com.youapps.onlybeans.android.notifications.data.data_sources.OBNotificationsLocalDataSource
import com.youapps.onlybeans.android.notifications.data.data_sources.OBNotificationsRemoteDataSource
import com.youapps.onlybeans.android.notifications.domain.entities.OBNotificationItemData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map


interface OBNotificationsRepository {

     fun fetchNotificationsByPagination(skip : Int,limit : Int) : Flow<List<OBNotificationItemData>>

     fun listenToNotificationUpdates() : Flow<List<OBNotificationItemData>>

    suspend fun updateNotificationReadStatus(id : String,isRead : Boolean) : Boolean

    suspend fun removeNotification(id : String) : Boolean

    suspend fun setAllCurrentNotificationsRead() : Boolean

     suspend fun getUnReadNotificationsCount() : Long


}


class OBNotificationsRepositoryImpl(
    private val localDataSource : OBNotificationsLocalDataSource,
    private val remoteDataSource : OBNotificationsRemoteDataSource
) : OBNotificationsRepository {

    override fun fetchNotificationsByPagination(
        skip: Int,
        limit: Int,
    ): Flow<List<OBNotificationItemData>> = remoteDataSource.fetchNotificationsByPagination(skip,limit)
        .catch {
          emit(localDataSource.fetchMostRecentNotifications())
        }
        .map { notificationNewDataPage->
            localDataSource.saveNotifications(notificationNewDataPage)
            notificationNewDataPage
        }

    override fun listenToNotificationUpdates(): Flow<List<OBNotificationItemData>> = TODO()




    override suspend fun updateNotificationReadStatus(
        id: String,
        isRead: Boolean
    ): Boolean  {
        localDataSource.updateNotificationReadStatus(id,isRead)
        return remoteDataSource.updateNotificationReadStatus(id,isRead)
    }

    override suspend fun removeNotification(
        id: String
    ): Boolean {
        localDataSource.removeNotification(id)
        return  remoteDataSource.removeNotification(id)
    }

    override suspend fun setAllCurrentNotificationsRead(): Boolean {
        localDataSource.setAllCurrentNotificationsRead()
        return  remoteDataSource.setAllCurrentNotificationsRead()
    }

    override  suspend fun getUnReadNotificationsCount(): Long {
        return  remoteDataSource.fetchUnReadNotificationsCount()
    }
}