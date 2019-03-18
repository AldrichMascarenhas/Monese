package com.test.data.local.mapper

import com.test.data.local.model.CachedLaunch
import com.test.data.network.response.RemoteLaunch

/**
 * Map a [RemoteLaunch] instance to and from a [CachedLaunch] instance when data is moving between
 * this later and the Data layer
 */
class CachedLaunchMapper() : CachedMapper<CachedLaunch, RemoteLaunch> {

    /**
     * Map a [RemoteLaunch] instance to a [CachedLaunch] instance
     */
    override fun mapToCached(type: RemoteLaunch): CachedLaunch {
        return CachedLaunch(
            flightNumber = type.flightNumber,
            rocketId = type.rocketId,
            launchYear = type.launchYear,
            missionName = type.missionName,
            launchSuccess = type.launchSuccess,
            launchDateUTC = type.launchDateUTC,
            missionPatchImage = type.missionPatchImage
        )
    }

}