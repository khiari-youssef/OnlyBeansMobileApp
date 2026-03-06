package com.youapps.onlybeans.android.notifications.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class NotificationScreenStateHolder(
    val notificationsListState: State<NotificationsListState>,
    val currentNotificationsCount: State<Long?>
) {

    companion object {

        @Composable
        fun rememberNotificationScreenState(
            notificationsListState: State<NotificationsListState>,
            currentNotificationsCount: State<Long?>
        ): NotificationScreenStateHolder = remember(notificationsListState, currentNotificationsCount) {
            NotificationScreenStateHolder(
                notificationsListState, currentNotificationsCount
            )
        }

        @Composable
        fun  bindViewModelState(viewModel: NotificationsViewModel) : NotificationScreenStateHolder = rememberNotificationScreenState(
        notificationsListState = viewModel.latestNotificationsState.collectAsStateWithLifecycle(),
            currentNotificationsCount = produceState(initialValue = null) {
                value = viewModel.getNotificationsCount()
            }
        )
    }
}