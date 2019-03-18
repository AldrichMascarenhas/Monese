package com.test.data.network.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.test.data.util.extensions.defensiveTry
import com.test.data.network.constants.*
import com.test.data.network.response.RemoteRocket
import com.test.data.network.response.RocketResponse
import java.lang.reflect.Type

class RocketDeserializer : JsonDeserializer<RocketResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): RocketResponse {

        var rocketResponse = RocketResponse(remoteRocketList = emptyList())

        json?.asJsonArray?.let {

            val rocketList = ArrayList<RemoteRocket>()

            it.mapTo(rocketList) {
                with(it as JsonObject) {

                    val id = defensiveTry(0) { get(ID).asInt }
                    val active = defensiveTry(false) { get(ACTIVE).asBoolean }
                    val country = defensiveTry("") { get(COUNTRY).asString }

                    val flickrImages = if (has(FLICKR_IMAGES)) {
                        with(it.get(FLICKR_IMAGES).asJsonArray) {
                            val flickrImages = ArrayList<String>()
                            mapTo(flickrImages) {
                                 it.asString
                            }
                            return@with flickrImages
                        }

                    } else emptyList<String>()

                    val rocketId = defensiveTry("") { get(ROCKET_ID).asString }
                    val rocketName = defensiveTry("") { get(ROCKET_NAME).asString }
                    val description = defensiveTry("") { get(DESCRIPTION).asString }
                    val engineCount = if (has(ENGINES)) {
                        with(it.get(ENGINES).asJsonObject) {
                            defensiveTry(0) { get(NUMBER).asInt }
                        }

                    } else 0

                    return@mapTo RemoteRocket(
                        id = id,
                        active = active,
                        country = country,
                        flickrImages = flickrImages,
                        rocketId = rocketId,
                        rocketName = rocketName,
                        description = description,
                        engineCount = engineCount
                    )
                }
            }
            rocketResponse = RocketResponse(remoteRocketList = rocketList)
        }
        return rocketResponse
    }
}