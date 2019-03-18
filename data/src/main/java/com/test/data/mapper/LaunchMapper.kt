package com.test.data.mapper

import com.test.data.local.model.CachedLaunch
import com.test.data.util.extensions.formatToLocalDate
import com.test.presentation.model.Launch

/**
 * Map a [CachedLaunch] to a [Launch] instance when data is moving between
 * this later and the Domain layer
 */
class LaunchMapper() : Mapper<CachedLaunch, Launch> {

    /**
     * Map a [CachedLaunch] instance to a [Launch] instance
     */

    override fun mapFromEntity(type: CachedLaunch): Launch {
        return Launch(
            flightNumber = type.flightNumber,
            rocketId = type.rocketId,
            launchYear = type.launchYear,
            missionName = type.missionName,
            launchSuccess = type.launchSuccess,
            launchDateUTC = type.launchDateUTC.formatToLocalDate(),
            missionPatchImage = type.missionPatchImage
        )
    }

}