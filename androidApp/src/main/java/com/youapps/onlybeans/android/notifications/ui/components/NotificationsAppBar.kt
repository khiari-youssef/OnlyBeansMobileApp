package com.youapps.onlybeans.android.notifications.ui.components



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.youapps.onlybeans.android.R
import com.youapps.onlybeans.designsystem.R as DesignSystemR

@Composable
fun NotificationsAppBar(
    unreadCount: Long?,
    onMarkAllRead: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(
                horizontal = 12.dp,
                vertical = 16.dp
            )
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
        ) {
            // Bell Icon
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = ImageVector.vectorResource(DesignSystemR.drawable.ic_notifications_outlined),
                contentDescription = stringResource(R.string.notifications_app_bar_icon_description),
            )
            // Title
            Text(
                text = stringResource(R.string.notifications_app_bar_title),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            // Orange Badge
            if ((unreadCount ?: 0) > 0) {
                Surface(
                    color = Color(0xFFE67E00), // Match that specific orange
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = unreadCount.toString(),
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }
        }
        Text(
            modifier = Modifier
                .weight(0.3f)
                .padding(8.dp)
                .clickable(onClick = onMarkAllRead)
                .fillMaxWidth(),
            text = stringResource(R.string.notifications_app_bar_mark_all_read),
            color = Color(0xFFAD4B00), // Slightly darker orange/brown for the text
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Medium
            ),
            textAlign = TextAlign.Start
        )
    }

}
