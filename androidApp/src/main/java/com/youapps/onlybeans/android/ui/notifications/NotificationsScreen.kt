package com.youapps.onlybeans.android.ui.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.youapps.designsystem.components.loading.shimmerEffect
import com.youapps.onlybeans.domain.entities.OBNotification

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier,
    screenState: NotificationScreenStateHolder,
    onProjectReferenceClicked: (String) -> Unit,
    onRefreshNotifications: () -> Unit
) {
    val notificationsListState = screenState.notificationsListState.value
    val isPullRefreshing = remember {
        derivedStateOf {
            (notificationsListState is NotificationsListState.NotificationsLoading && notificationsListState.isRefresh)
        }
    }
    val listState = rememberLazyListState()
    val refreshState = rememberPullToRefreshState()
    PullToRefreshBox(
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
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
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
                when (notificationsListState) {
                    is NotificationsListState.NotificationsLoading -> {
                        items(15) {
                            NotificationItem(
                                modifier = Modifier
                                    .height(60.dp)
                                    .fillMaxWidth(),
                                oBNotification = null,
                                onProjectReferenceClicked = {}
                            )
                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 12.dp
                                    )
                                    .fillMaxWidth(),
                                thickness = 0.5f.dp,
                                color = DividerDefaults.color
                            )
                        }
                    }

                    is NotificationsListState.Error -> {

                    }

                    is NotificationsListState.Success -> {
                        items(
                            items = notificationsListState.notificationsList,
                            key = {
                                it.id
                            }
                        ) { notification ->
                            val itemModifier = Modifier
                                .shimmerEffect(notificationsListState.isRefreshingMore)
                                .fillMaxWidth()
                            when (notification) {
                                is OBNotification.OBRequestNotification -> {
                                    NotificationRequestItem(
                                        modifier = itemModifier,
                                        oBNotification = notification,
                                        onProjectReferenceClicked = onProjectReferenceClicked
                                    )
                                }

                                is OBNotification.OBInfoNotification -> {
                                    NotificationItem(
                                        modifier = itemModifier,
                                        oBNotification = notification,
                                        onProjectReferenceClicked = onProjectReferenceClicked
                                    )
                                }

                                is OBNotification.OBResponseNotification -> {
                                    NotificationResponseItem(
                                        modifier = itemModifier,
                                        OBNotification = notification,
                                        onProjectReferenceClicked = onProjectReferenceClicked
                                    )
                                }
                            }
                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 12.dp
                                    )
                                    .fillMaxWidth(),
                                thickness = 0.5f.dp,
                                color = DividerDefaults.color
                            )
                        }
                    }
                }
            }
        }
    }


}