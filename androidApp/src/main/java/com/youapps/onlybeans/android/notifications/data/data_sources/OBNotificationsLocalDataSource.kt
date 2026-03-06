package com.youapps.onlybeans.android.notifications.data.data_sources

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.youapps.onlybeans.OnlyBeansDatabase
import com.youapps.onlybeans.android.notifications.domain.entities.NotificationItemType
import com.youapps.onlybeans.android.notifications.domain.entities.OBNotificationItemData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


interface OBNotificationsLocalDataSource {

    suspend fun fetchMostRecentNotifications() : List<OBNotificationItemData>

    suspend fun updateNotificationReadStatus(id : String,isRead : Boolean) : Boolean

    suspend fun removeNotification(id : String) : Boolean

    suspend fun setAllCurrentNotificationsRead() : Boolean

    suspend fun saveNewNotification(obNotificationItemData: OBNotificationItemData) : Boolean

    suspend fun saveNotifications(obNotificationItemsData: List<OBNotificationItemData>) : Boolean

     fun getUnReadNotificationsCount(): Flow<Long>
}


class OBNotificationsLocalDataSourceImpl(
    val appDatabase: OnlyBeansDatabase
) : OBNotificationsLocalDataSource {


    override suspend fun fetchMostRecentNotifications(): List<OBNotificationItemData> =
        appDatabase.onlyBeansDatabaseQueries.selectAllMostRecentNotifications(limitparam = 20L)
            .executeAsList().map { dbo->
                    OBNotificationItemData(
                        id = dbo.id,
                        title = dbo.title,
                        description = dbo.description,
                        timeAgo = dbo.timeAgo,
                        isRead = dbo.isRead,
                        type = NotificationItemType.valueOf(
                            dbo.type
                        )
                    )
            }


    override suspend fun updateNotificationReadStatus(
        id: String,
        isRead: Boolean
    ): Boolean = withContext(Dispatchers.IO){
        appDatabase.onlyBeansDatabaseQueries.updateNotificationReadStatusByID(isRead = isRead, id = id) > 0
    }

    override suspend fun removeNotification(id: String): Boolean =
        withContext(Dispatchers.IO) { appDatabase.onlyBeansDatabaseQueries.deleteNotificationById(id) > 0 }

    override suspend fun setAllCurrentNotificationsRead(): Boolean  =
        withContext(Dispatchers.IO) { appDatabase.onlyBeansDatabaseQueries.updateAllNotificationsReadStatus() > 0 }

    override suspend fun saveNewNotification(obNotificationItemData: OBNotificationItemData): Boolean
    =  withContext(Dispatchers.IO) {
        appDatabase.onlyBeansDatabaseQueries.insertNotification(
            id = obNotificationItemData.id,
            title = obNotificationItemData.title,
            description = obNotificationItemData.description,
            isRead = obNotificationItemData.isRead,
            timeAgo = obNotificationItemData.timeAgo,
            type = obNotificationItemData.type.toString()
        ) > 0
    }

    override suspend fun saveNotifications(obNotificationItemsData: List<OBNotificationItemData>): Boolean =withContext(Dispatchers.IO) {
       appDatabase.transactionWithResult {
           obNotificationItemsData.forEach {obNotificationItemData->
               runCatching {
                   appDatabase.onlyBeansDatabaseQueries.insertNotification(
                       id = obNotificationItemData.id,
                       title = obNotificationItemData.title,
                       description = obNotificationItemData.description,
                       isRead = obNotificationItemData.isRead,
                       timeAgo = obNotificationItemData.timeAgo,
                       type = obNotificationItemData.type.toString()
                   ) > 0
               }.onFailure {
                   rollback(false)
               }
           }
           return@transactionWithResult true
       }
    }

    override  fun getUnReadNotificationsCount(): Flow<Long> =
        appDatabase.onlyBeansDatabaseQueries.selectUnReadNotificationsCount().asFlow().mapToOne(Dispatchers.IO)


}