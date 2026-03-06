package com.youapps.onlybeans.android.notifications.domain.entities

enum class NotificationItemType{
    ORDER,PROMO,REWARD,GENERAL
}

data class OBNotificationItemData(
    val id : String,
    val title: String,
    val description: String,
    val timeAgo: Long,
    val isRead: Boolean = false,
    val type : NotificationItemType = NotificationItemType.GENERAL
) {
    val displayTimeAgo : String = formatDuration(timeAgo)

    private  fun formatDuration(ms: Long): String {
        val seconds = ms / 1000.0
        val minutes = seconds / 60.0
        val hours = minutes / 60.0
        val days = hours / 24.0

        return when {
            days >= 1.0 -> "%.1f days".format(days)
            hours >= 1.0 -> "%.1f hours".format(hours)
            minutes >= 1.0 -> "%.1f minutes".format(minutes)
            seconds >= 1.0 -> "%.1f seconds".format(seconds)
            else -> "$ms ms"
        }
    }
}