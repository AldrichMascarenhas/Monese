package com.test.data.local.mapper

import com.test.data.local.model.CachedRocket
import com.test.data.network.response.RemoteRocket

/**
 * Map a [RemoteRocket] instance to and from a [CachedRocket] instance when data is moving between
 * this later and the Data layer
 */
 class CachedRocketMapper() : CachedMapper<CachedRocket, RemoteRocket> {

    /**
     * Map a [RemoteRocket] instance to a [CachedRocket] instance
     */
    override fun mapToCached(type: RemoteRocket): CachedRocket {
        return CachedRocket(
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