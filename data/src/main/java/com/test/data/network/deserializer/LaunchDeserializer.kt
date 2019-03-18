package com.test.data.network.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.test.data.util.extensions.defensiveTry
import com.test.data.network.constants.*
import com.test.data.network.response.RemoteLaunch
import com.test.data.network.response.LaunchResponse
import java.lang.reflect.Type

class LaunchDeserializer : JsonDeserializer<LaunchResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LaunchResponse {

        var launchResponse = LaunchResponse(remoteLaunchList = emptyList())

        json?.asJsonArray?.let {

            val launchList = ArrayList<RemoteLaunch>()

            it.mapTo(launchList) {
                with(it as JsonObject) {

                    val flightNumber = defensiveTry(0) { get(FLIGHT_NUMBER).asInt }

                    val rocketId = if (has(ROCKET)) {
                        with(it.get(ROCKET).asJsonObject) {
                            return@with defensiveTry("") { get(ROCKET_ID).asString }
                        }
                    } else ""
                    val launchYear = defensiveTry(0) { get(LAUNCH_YEAR).asInt }
                    val missionName = defensiveTry("") { get(MISSION_NAME).asString }
                    val launchSuccess = defensiveTry(false) { get(LAUNCH_SUCCESS).asBoolean }

                    val launchDateUTC = defensiveTry("") { get(LAUNCH_DATE_UTC).asString }

                    val missionPatchImage = if (has(LINKS)) {
                        with(it.get(LINKS).asJsonObject) {
                            return@with defensiveTry("") { get(MISSION_PATCH).asString }
                        }
                    } else ""

                    return@mapTo RemoteLaunch(
                        flightNumber = flightNumber,
                        rocketId = rocketId,
                        launchYear = launchYear,
                        missionName = missionName,
                        launchSuccess = launchSuccess,
                        launchDateUTC = launchDateUTC,
                        missionPatchImage = missionPatchImage
                    )
                }
            }
            launchResponse = LaunchResponse(remoteLaunchList = launchList)
        }
        return launchResponse
    }
}