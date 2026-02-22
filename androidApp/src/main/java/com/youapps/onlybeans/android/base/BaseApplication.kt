package com.youapps.onlybeans.android.base

import android.app.Application
import com.youapps.onlybeans.android.di.viewModelsModule
import com.youapps.onlybeans.di.androidSecurityModule
import com.youapps.onlybeans.di.platformServicesModule
import com.youapps.search_module.search_list_map.di.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin


class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            loadKoinModules(
               listOf(
                   searchModule,
                   viewModelsModule,
                   androidSecurityModule,
                   platformServicesModule,
               )
            )
        }

    }
}