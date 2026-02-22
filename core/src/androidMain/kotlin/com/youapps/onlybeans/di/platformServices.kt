package com.youapps.onlybeans.di


import com.youapps.onlybeans.platform.OBLocationService
import com.youapps.onlybeans.platform.OBLocationServicePlayServicesImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val OBLocationServicePlayServicesImplTag = named("OBLocationServicePlayServicesImpl")

val platformServicesModule = module {

    single<OBLocationService>(OBLocationServicePlayServicesImplTag) {
        OBLocationServicePlayServicesImpl(get())
    }

}