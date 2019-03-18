package com.test.data.network.response

data class LaunchResponse(
    val remoteLaunchList: List<RemoteLaunch>
)

data class RemoteLaunch(
    val flightNumber : Int,
    val rocketId : String,
    val launchYear : Int,
    val missionName : String,
    val launchSuccess : Boolean,
    val launchDateUTC : String,
    val missionPatchImage : String
)