package com.test.monese.di

import android.content.Context
import com.test.data.local.SpaceXDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module
import androidx.room.Room

/**
 * Koin Module to provide dependenices for all Database Related Classes
 */
val databaseModule = module {

    single { createMoneseDatabase(androidApplication()) }

}

fun createMoneseDatabase(context: Context): SpaceXDatabase =
    Room.databaseBuilder(context, SpaceXDatabase::class.java, "spacex_db").build()

