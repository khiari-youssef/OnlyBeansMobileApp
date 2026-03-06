package com.youapps.onlybeans.android.notifications.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.youapps.onlybeans.android.notifications.domain.entities.OBNotificationItemData
import com.youapps.onlybeans.designsystem.R


@Preview
@Composable
fun OBNotificationCardPreview(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        OBNotificationCard(
            modifier = Modifier,
        notification = OBNotificationItemData(
            id = "notifID",
            title = "Order Ready for Pickup",
            description = "Your Caramel Macchiato is ready at Downtown Café",
            timeAgo = 60000,
            isRead = true
        ),
        onDismiss= {}
        )
    }
}


@Composable
fun OBNotificationCard(
    modifier: Modifier = Modifier,
    notification: OBNotificationItemData,
    onDismiss: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border =  BorderStroke(1.dp, color =if (notification.isRead) Color(0xFFfde68a) else Color(0xFFe7e5e4)),
        shape = RoundedCornerShape(12.dp)// Light grey border
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(
                    12.dp
                )
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (notifIcon,notifContent,notifStatus,notifCancel) = createRefs()
            NotificationTypeBox(
                modifier = Modifier
                    .constrainAs(notifIcon){
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                type = notification.type
            )
            Column(
                modifier = Modifier.constrainAs(notifContent){
                    start.linkTo(notifIcon.end,12.dp)
                    top.linkTo(parent.top)
                    end.linkTo(notifCancel.start)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = notification.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = notification.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = notification.displayTimeAgo,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.LightGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .constrainAs(notifCancel) {
                        top.linkTo(notifContent.top)
                        bottom.linkTo(notifContent.bottom)
                        end.linkTo(parent.end)
                    }
                    .padding(4.dp)
                    .wrapContentSize()
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_cross),
                    contentDescription = "Dismiss",
                    tint = Color.LightGray,
                    modifier = Modifier.size(24.dp)
                )
            }
            if (notification.isRead.not()) {
                Box(
                    modifier = Modifier
                        .constrainAs(notifStatus) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                        .size(8.dp)
                        .background(Color(0xFFE67E22), CircleShape)
                )
            }
        }

    }

}




