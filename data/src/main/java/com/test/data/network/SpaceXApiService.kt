package com.test.data.network

import com.test.data.network.response.LaunchResponse
import com.test.data.network.response.RocketResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceXApiService {

    /**
     * Returns all rockets
     */
    @GET("rockets")
    fun getAllRockets() : Single<RocketResponse>

    @GET("launches")
    fun getRocket(@Query("rocket_id") rocketID: String) : Single<RocketResponse>

    @GET("launches")
    fun getLaunch(@Query("rocket_id") rocketID: String) : Single<LaunchResponse>

}