package com.test.data.repository

import com.test.data.preferences.PreferencesHelper
import com.test.presentation.repository.PreferencesRepositoryContract

class PreferencesRepositoryImpl(
    private val preferencesHelper: PreferencesHelper
) : PreferencesRepositoryContract {


    override fun getFirstLaunch() = preferencesHelper.isFirstLaunch

    override fun setFirstLaunch() {
        preferencesHelper.isFirstLaunch = false
    }

}