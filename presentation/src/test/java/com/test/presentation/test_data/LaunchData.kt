import com.test.presentation.model.Launch

object LaunchData {

    val launch1 = Launch(
        flightNumber = 12,
        rocketId = "3",
        launchYear = 2019,
        missionName = "",
        launchSuccess = false,
        launchDateUTC = "",
        missionPatchImage = ""
    )

    val launch2 = Launch(
        flightNumber = 12,
        rocketId = "3",
        launchYear = 2019,
        missionName = "",
        launchSuccess = false,
        launchDateUTC = "",
        missionPatchImage = ""
    )

    val launch3 = Launch(
        flightNumber = 12,
        rocketId = "3",
        launchYear = 2010,
        missionName = "",
        launchSuccess = false,
        launchDateUTC = "",
        missionPatchImage = ""
    )

    val launchList = listOf(launch1, launch2, launch3)

}