import com.test.data.local.model.CachedLaunch

object CachedLaunch {

    val launch1 = CachedLaunch(
        flightNumber = 12,
        rocketId = "3",
        launchYear = 2019,
        missionName = "",
        launchSuccess = false,
        launchDateUTC = "",
        missionPatchImage = ""
    )

    val launch2 = CachedLaunch(
        flightNumber = 12,
        rocketId = "3",
        launchYear = 2019,
        missionName = "",
        launchSuccess = false,
        launchDateUTC = "",
        missionPatchImage = ""
    )

    val launch3 = CachedLaunch(
        flightNumber = 12,
        rocketId = "3",
        launchYear = 2010,
        missionName = "",
        launchSuccess = false,
        launchDateUTC = "",
        missionPatchImage = ""
    )

    val cachedLaunchList = listOf(launch1, launch2, launch3)

}