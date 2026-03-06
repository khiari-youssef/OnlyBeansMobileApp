package com.youapps.onlybeans.android.notifications.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youapps.onlybeans.android.notifications.data.repositories.OBNotificationsRepository
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotificationsViewModel(
    private val repository: OBNotificationsRepository
) : ViewModel() {


    private val _latestNotificationsState: MutableStateFlow<NotificationsListState> =
        MutableStateFlow(NotificationsListState.NotificationsLoading.DefaultLoading)
    val latestNotificationsState: StateFlow<NotificationsListState> = _latestNotificationsState


    init {
        getLastNotifications()
    }


    suspend fun getNotificationsCount() : Long = repository.getUnReadNotificationsCount()

    fun getLastNotifications(
        isPullToRefresh: Boolean = false
    ) {
        viewModelScope.launch {
            _latestNotificationsState.update {
               if(isPullToRefresh) NotificationsListState.NotificationsLoading.PullToRefreshLoading else NotificationsListState.NotificationsLoading.DefaultLoading
            }
            repository.fetchNotificationsByPagination(
                skip = 0,
                limit = 8
            ).catch {
                _latestNotificationsState.update {
                    NotificationsListState.Error()
                }
            }.collect { data->
                _latestNotificationsState.update {
                    NotificationsListState.PaginationState.NextPageLoaded(data.toImmutableList())
                }
            }
        }
    }


    fun loadMoreNotifications(
        pageSize: Int
    ){
        val currentState = _latestNotificationsState.value
        if (currentState is NotificationsListState.Error) {
            // if an error has recently happened, then we only need to refresh the list once again
            getLastNotifications()
            return
        }
        if (currentState is NotificationsListState.PaginationState.NextPageLoaded || currentState is NotificationsListState.PaginationState.NextPageNotLoaded) {
            // we only load next page if there is no current loading state
            // we also need the current page state to calculate next page
            viewModelScope.launch {
                _latestNotificationsState.update {
                    NotificationsListState.PaginationState.LoadingNextPage(
                        currentNotificationList = currentState.currentNotificationList
                    )
                }
                repository.fetchNotificationsByPagination(
                    skip = currentState.currentNotificationList.size,
                    limit = pageSize
                ).catch {
                    _latestNotificationsState.update {
                        NotificationsListState.PaginationState.NextPageNotLoaded(currentState.currentNotificationList)
                    }
                }.collect { newNotificationPage->
                    _latestNotificationsState.update {
                        NotificationsListState.PaginationState.NextPageLoaded(
                            (currentState.currentNotificationList + newNotificationPage).toImmutableList()
                        )
                    }
                }
            }
        }
        // we don't need to do anything if there is an ongoing loading state
    }

    fun removeNotification(id : String){
        viewModelScope.launch {
            repository.removeNotification(id)
        }
    }

    fun markAllNotificationsRead() {
        viewModelScope.launch {
               repository.setAllCurrentNotificationsRead()
        }
    }

    fun markNotificationRead(id : String) {
        viewModelScope.launch {
               repository.updateNotificationReadStatus(id,true)
        }
    }

}