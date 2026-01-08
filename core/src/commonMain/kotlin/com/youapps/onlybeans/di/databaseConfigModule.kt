package com.youapps.onlybeans.di

import com.youapps.onlybeans.data.dto.OBFileDTO
import kotlinx.serialization.json.Json
import org.koin.core.module.Module

internal const val DATABASE_FILE_NAME : String = "AppDatabase.db"



internal expect val databaseModule : Module

