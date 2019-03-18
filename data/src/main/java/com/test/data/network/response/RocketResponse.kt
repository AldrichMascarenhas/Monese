package com.test.data.network.response


data class RocketResponse(
    val remoteRocketList: List<RemoteRocket>
)

data class RemoteRocket(
    val id: Int,
    val active: Boolean,
    val country: String,
    val flickrImages: List<String>,
    val rocketId: String,
    val rocketName: String,
    val description : String,
    val engineCount: Int
)

