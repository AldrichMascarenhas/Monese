package com.test.monese

import android.app.Application
import com.test.monese.di.*
import org.koin.android.ext.android.startKoin

class MoneseApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin(
            this,
            listOf(coreModule, remoteDataSourceModule, repositoryModule, viewModelModule, databaseModule, dataMapperModule),
            loadPropertiesFromFile = true
        )
    }
}