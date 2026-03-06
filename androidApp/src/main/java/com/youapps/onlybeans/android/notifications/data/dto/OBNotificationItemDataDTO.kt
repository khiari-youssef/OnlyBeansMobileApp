package com.youapps.onlybeans.android.notifications.data.dto

import com.youapps.onlybeans.android.notifications.domain.entities.NotificationItemType
import com.youapps.onlybeans.android.notifications.domain.entities.OBNotificationItemData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class OBNotificationItemDataDTO(
    @SerialName("id")  val id : String,
    @SerialName("title")  val title: String,
    @SerialName("description") val description: String,
    @SerialName("timeAgo") val timeAgo: Long,
    @SerialName("isRead") val isRead: Boolean = false,
    @SerialName("type") val type : String = "GENERAL"
) {
    fun toDomainModel() : OBNotificationItemData? = runCatching {
        OBNotificationItemData(
            id = this.id,
            title = this.title,
            description = this.description,
            timeAgo = this.timeAgo,
            isRead = this.isRead,
            type = NotificationItemType.valueOf(this.type)
        )
    }.getOrNull()
}