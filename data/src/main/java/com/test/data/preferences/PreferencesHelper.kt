package com.test.data.preferences

import android.content.Context
import android.content.SharedPreferences

/**
 * Preferences Helper
 * Used for storing preference values using the Preference API
 */
class PreferencesHelper constructor(context: Context) {

    companion object {
        private val PREF_PACKAGE_NAME = "com.test.monese.preferences"

        private val PREF_KEY_FIRST_LAUNCH = "first_launch"
    }

    private val monesePreferencesHelper: SharedPreferences

    init {
        monesePreferencesHelper = context.getSharedPreferences(PREF_PACKAGE_NAME, Context.MODE_PRIVATE)
    }

    var isFirstLaunch: Boolean
        get() = monesePreferencesHelper.getBoolean(PREF_KEY_FIRST_LAUNCH, true)
        set(firstLaunch) = monesePreferencesHelper.edit().putBoolean(PREF_KEY_FIRST_LAUNCH, firstLaunch).apply()

}
