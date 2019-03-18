package com.test.monese.di

import com.test.data.preferences.PreferencesHelper
import com.test.data.repository.*
import com.test.presentation.repository.LaunchRepositoryContract
import com.test.presentation.repository.PreferencesRepositoryContract
import com.test.presentation.repository.RocketRepositoryContract
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val repositoryModule = module {

    single {
        RocketRepository(
            spaceXApiService = get(),
            spaceXDatabase = get(),
            cachedRocketMapper = get(),
            schedulerProvider = get()
        )
    }

    single<RocketRepositoryContract> {
        RocketRepositoryImpl(
            rocketRepository = get(),
            rocketMapper = get()
        )
    }


    single {
        LaunchRepository(
            spaceXApiService = get(),
            spaceXDatabase = get(),
            cachedLaunchMapper = get(),
            schedulerProvider = get()
        )
    }

    single<LaunchRepositoryContract> {
        LaunchRepositoryImpl(
            launchRepository = get(),
            launchMapper = get()
        )
    }

    single {
        PreferencesHelper(
            context = androidApplication()
        )
    }

    single<PreferencesRepositoryContract> {
        PreferencesRepositoryImpl(
            preferencesHelper = get()
        )
    }

}