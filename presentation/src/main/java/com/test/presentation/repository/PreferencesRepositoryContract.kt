package com.test.presentation.repository


/**
 * Interface defining methods for how the data layer can pass data to Presentation layer
 */
interface PreferencesRepositoryContract {

    fun getFirstLaunch() : Boolean

    fun setFirstLaunch()
}