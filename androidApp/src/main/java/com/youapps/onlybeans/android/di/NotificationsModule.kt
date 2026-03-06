package com.youapps.onlybeans.android.di

import com.youapps.onlybeans.android.notifications.data.data_sources.OBNotificationsLocalDataSource
import com.youapps.onlybeans.android.notifications.data.data_sources.OBNotificationsLocalDataSourceImpl
import com.youapps.onlybeans.android.notifications.data.data_sources.OBNotificationsRemoteDataSource
import com.youapps.onlybeans.android.notifications.data.data_sources.OBNotificationsRemoteDataSourceImpl
import com.youapps.onlybeans.android.notifications.data.repositories.OBNotificationsRepository
import com.youapps.onlybeans.android.notifications.data.repositories.OBNotificationsRepositoryImpl
import com.youapps.onlybeans.android.notifications.ui.screen.NotificationsViewModel
import com.youapps.onlybeans.di.dataConfigModule
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val OBNotificationsLocalDataSourceTag = named("OBNotificationsLocalDataSourceTag")
val OBNotificationsRemoteDataSourceTag = named("OBNotificationsRemoteDataSourceTag")

val OBNotificationsRepositoryTag = named("OBNotificationsRepositoryTag")

val notificationsModule = module{
    includes(dataConfigModule)
    factory<OBNotificationsLocalDataSource>(OBNotificationsLocalDataSourceTag){
        OBNotificationsLocalDataSourceImpl(
            get()
        )
    }
    factory<OBNotificationsRemoteDataSource>(OBNotificationsRemoteDataSourceTag){OBNotificationsRemoteDataSourceImpl() }

    factory<OBNotificationsRepository>(OBNotificationsRepositoryTag) {
        OBNotificationsRepositoryImpl(
            get(OBNotificationsLocalDataSourceTag),
            get(OBNotificationsRemoteDataSourceTag)
        )
    }

    viewModel {
        NotificationsViewModel(
            get(OBNotificationsRepositoryTag)
        )
    }
}