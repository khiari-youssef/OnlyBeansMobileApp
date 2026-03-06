package com.youapps.onlybeans.android.notifications.ui.screen

import com.youapps.onlybeans.android.notifications.domain.entities.OBNotificationItemData
import com.youapps.onlybeans.domain.exception.DomainErrorType
import kotlinx.collections.immutable.ImmutableList


sealed interface NotificationsListState {


    sealed interface NotificationsLoading : NotificationsListState {
        data object DefaultLoading : NotificationsLoading
        data object PullToRefreshLoading : NotificationsLoading
    }

    data class Error(val domainErrorType: DomainErrorType = DomainErrorType.Undefined) : NotificationsListState

    sealed class  PaginationState(
        open val currentNotificationList : ImmutableList<OBNotificationItemData>,
    ) : NotificationsListState {

        data class LoadingNextPage(override  val currentNotificationList : ImmutableList<OBNotificationItemData>) : PaginationState(currentNotificationList)

        data class NextPageLoaded(override val currentNotificationList : ImmutableList<OBNotificationItemData>) : PaginationState(currentNotificationList)

        data class NextPageNotLoaded(override val currentNotificationList : ImmutableList<OBNotificationItemData>, val domainErrorType: DomainErrorType = DomainErrorType.Undefined) : PaginationState(currentNotificationList)
    }
}