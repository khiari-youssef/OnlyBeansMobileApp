package com.youapps.onlybeans.android.notifications.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.youapps.onlybeans.android.notifications.domain.entities.NotificationItemType
import com.youapps.onlybeans.designsystem.R


@Composable
fun NotificationTypeBox(
    modifier: Modifier = Modifier,
    type : NotificationItemType
) {
    val backgroundColor : Color = when(type){
        NotificationItemType.ORDER -> {
            Color(0xFFdbeafe)
        }
        NotificationItemType.PROMO -> {
            Color(0xFFfef3c7)
        }
        NotificationItemType.REWARD -> {
            Color(0xFFf3e8ff)
        }
        NotificationItemType.GENERAL -> {
            Color(0xFFf5f5f4)
        }
    }
    val backgroundIconRes : Int = when(type){
        NotificationItemType.ORDER -> {
            R.drawable.ic_shopping_bag
        }
        NotificationItemType.PROMO -> {
            R.drawable.ic_notification_promo
        }
        NotificationItemType.REWARD -> {
            R.drawable.ic_notification_reward
        }
        NotificationItemType.GENERAL -> {
            R.drawable.ic_notification_default
        }
    }
    val backgroundIconTint : Color = when(type){
        NotificationItemType.ORDER -> {
            Color(0xFF1A73E8)
        }
        NotificationItemType.PROMO -> {
            Color(0xFFd97706)
        }
        NotificationItemType.REWARD -> {
            Color(0xFF9333ea)
        }
        NotificationItemType.GENERAL -> {
            Color(0xFF57534e)
        }
    }

    Box(
        modifier = modifier
            .size(40.dp)
            .background(backgroundColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(backgroundIconRes),
            contentDescription = null,
            tint = backgroundIconTint,
            modifier = Modifier.size(20.dp)
        )
    }
}