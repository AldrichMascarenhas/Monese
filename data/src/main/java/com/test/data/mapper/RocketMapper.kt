package com.test.data.mapper

import com.test.data.local.model.CachedRocket
import com.test.presentation.model.Rocket

/**
 * Map a [CachedRocket] to a [Rocket] instance when data is moving between
 * this later and the Domain layer
 */
class RocketMapper() : Mapper<CachedRocket, Rocket> {

    /**
     * Map a [CachedRocket] instance to a [Rocket] instance
     */
    override fun mapFromEntity(type: CachedRocket): Rocket {
        return Rocket(
            id = type.id,
            active = type.active,
            country = type.country,
            flickrImages = type.flickrImages,
            rocketId = type.rocketId,
            rocketName = type.rocketName,
            description = type.description,
            engineCount = type.engineCount
        )
    }

}