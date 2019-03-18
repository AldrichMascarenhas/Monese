package com.test.monese.di

import com.test.presentation.viewmodel.LaunchDetailViewModel
import com.test.presentation.viewmodel.MainViewModel
import com.test.presentation.viewmodel.RocketListViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {

    viewModel {
        RocketListViewModel(rocketRepositoryContract = get())
    }

    viewModel {
        LaunchDetailViewModel(
            launchRepositoryContract = get(),
            rocketRepositoryContract = get()
        )
    }

    viewModel {
        MainViewModel(
            preferencesRepositoryContract = get()
        )
    }
}