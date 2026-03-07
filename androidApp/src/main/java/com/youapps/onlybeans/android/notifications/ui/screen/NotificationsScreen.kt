package com.youapps.onlybeans.android.notifications.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.youapps.designsystem.components.loading.shimmerEffect
import com.youapps.designsystem.components.templates.EmptyStateComponent
import com.youapps.designsystem.components.templates.ErrorStateComponent
import com.youapps.onlybeans.android.R
import com.youapps.onlybeans.android.notifications.domain.entities.OBNotificationItemData
import com.youapps.onlybeans.android.notifications.ui.components.NotificationsAppBar
import com.youapps.onlybeans.android.notifications.ui.components.OBNotificationCard
import com.youapps.onlybeans.android.notifications.ui.components.OBNotificationCardLoader

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier,
    screenState: NotificationScreenStateHolder,
    onNotificationClicked: (id : String) -> Unit,
    onRemoveNotification: (id : String)-> Unit,
    onLoadNextPage: () -> Unit,
    onRefreshNotifications: () -> Unit,
    onMarkAllRead: () -> Unit
) {
    val notificationsListState = screenState.notificationsListState.value
    val isPullRefreshing = remember {
        derivedStateOf {
            notificationsListState is NotificationsListState.NotificationsLoading.PullToRefreshLoading
        }
    }
    val listState = rememberLazyListState()
    listState.OnBottomReached(
        onLoadMore = onLoadNextPage
    )
    val refreshState = rememberPullToRefreshState()
    Scaffold(
        topBar = {
            NotificationsAppBar(
                unreadCount = screenState.currentNotificationsCount.value,
                onMarkAllRead = onMarkAllRead
            )
        }
    ) { paddingValues ->
    PullToRefreshBox(
        modifier = Modifier
            .padding(paddingValues),
        isRefreshing = isPullRefreshing.value,
        onRefresh = onRefreshNotifications,
        state = refreshState,
        contentAlignment = Alignment.TopCenter,
        indicator = {
            PullToRefreshDefaults.Indicator(
                modifier = Modifier
                    .height(40.dp)
                    .zIndex(4f),
                containerColor = MaterialTheme.colorScheme.background,
                state = refreshState,
                isRefreshing = isPullRefreshing.value,
            )
        }
    ){
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
                when (notificationsListState) {
                    is  NotificationsListState.NotificationsLoading-> {
                        Column(
                            modifier = modifier
                                .padding(
                                    top = 8.dp,
                                )
                                .padding(
                                    horizontal = 12.dp
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(
                                8.dp, Alignment.CenterVertically
                            ),
                        ){
                           repeat(12){
                               OBNotificationCardLoader()
                           }
                        }

                    }
                    is NotificationsListState.Error -> {
                            ErrorStateComponent(
                             modifier = Modifier.fillMaxSize(),
                             title = stringResource(R.string.notifications_error_state_title),
                             subtitle = stringResource(R.string.notifications_error_state_subtitle),
                             buttonText = stringResource(com.youapps.onlybeans.designsystem.R.string.retry),
                             onButtonClick = onRefreshNotifications
                            )
                    }
                    is NotificationsListState.PaginationState -> {
                        if (notificationsListState.currentNotificationList.isEmpty()){
                                EmptyStateComponent(
                                    modifier = Modifier.fillMaxSize(),
                                    icon = ImageVector.vectorResource(com.youapps.onlybeans.designsystem.R.drawable.ic_notifications_outlined),
                                    title = stringResource(R.string.notifications_empty_state_title),
                                    subtitle = stringResource(R.string.notifications_empty_state_subtitle)
                                )
                        } else {
                            LazyColumn(
                                modifier = modifier
                                    .padding(
                                        top = 8.dp,
                                    )
                                    .padding(
                                        horizontal = 12.dp
                                    )
                                    .background(
                                        color = MaterialTheme.colorScheme.surfaceVariant
                                    ),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(
                                    8.dp, Alignment.CenterVertically
                                ),
                                state = listState
                            ) {
                                items(
                                    items = notificationsListState.currentNotificationList,
                                    key = {
                                        it.id
                                    }
                                ) { notification ->
                                    val itemModifier = Modifier
                                        .clickable(onClick = {
                                            onNotificationClicked(notification.id)
                                        })
                                        .fillMaxWidth()
                                    OBNotificationCard(
                                        modifier = itemModifier,
                                        notification = notification,
                                        onDismiss = {
                                            onRemoveNotification(notification.id)
                                        }
                                    )
                                }
                            }

                        }


                    }
                }

            AnimatedVisibility(
                modifier = Modifier.align(
                    Alignment.BottomCenter
                ).padding(
                    bottom = 12.dp
                ),
                visible = notificationsListState is NotificationsListState.PaginationState.LoadingNextPage,
                enter = slideInVertically(tween()),
                exit = slideOutVertically(tween())
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    backgroundColor = Color.White
                )
            }

        }
    }
    }


}



@Composable
private fun LazyListState.OnBottomReached(
    onLoadMore: () -> Unit
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            canScrollForward.not()
        }
    }

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .collect { if (it) onLoadMore() }
    }
}