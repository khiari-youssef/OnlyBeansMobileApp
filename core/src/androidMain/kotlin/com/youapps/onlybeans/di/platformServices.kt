package com.youapps.onlybeans.di


import com.youapps.onlybeans.platform.location.OBLocationService
import com.youapps.onlybeans.platform.location.OBLocationServicePlayServicesImpl
import com.youapps.onlybeans.platform.network.OBNetworkMonitor
import com.youapps.onlybeans.platform.network.OBNetworkMonitorImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val OBLocationServicePlayServicesImplTag = named("OBLocationServicePlayServicesImpl")
val OBNetworkMonitorImplTag = named("OBNetworkMonitorImpl")

val platformServicesModule = module {

    single<OBLocationService>(OBLocationServicePlayServicesImplTag) {
        OBLocationServicePlayServicesImpl(get())
    }

    single<OBNetworkMonitor>(OBNetworkMonitorImplTag) {
        OBNetworkMonitorImpl(get())
    }

}