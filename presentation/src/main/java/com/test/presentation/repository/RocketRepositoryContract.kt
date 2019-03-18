package com.test.presentation.repository

import androidx.lifecycle.LiveData
import com.test.presentation.model.Rocket
import com.test.presentation.resource.Resource

/**
 * Interface defining methods for how the data layer can pass data to Presentation layer
 */
interface RocketRepositoryContract {

    //Used to get all Rockets
    fun getRockets(): LiveData<Resource<List<Rocket>>>

    //Used to get all Rockets
    fun getRocket(rocketID: String): LiveData<Resource<Rocket>>

}