package com.test.presentation.repository

import androidx.lifecycle.LiveData
import com.test.presentation.model.Launch
import com.test.presentation.resource.Resource

/**
 * Interface defining methods for how the data layer can pass data to Presentation layer
 */
interface LaunchRepositoryContract {

    fun getLaunches(rocketID: String) : LiveData<Resource<List<Launch>>>

}