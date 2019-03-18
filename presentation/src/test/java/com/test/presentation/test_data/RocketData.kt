import com.test.presentation.model.Rocket

object RocketData{


    val rocket1 = Rocket(
        id = 1,
        active = true,
        country = "",
        flickrImages = emptyList(),
        rocketId = "",
        rocketName = "",
        description = "",
        engineCount = 0
    )

    val rocket2 = Rocket(
        id = 2,
        active = true,
        country = "",
        flickrImages = emptyList(),
        rocketId = "",
        rocketName = "",
        description = "",
        engineCount = 0
    )

    val rocket3 = Rocket(
        id = 3,
        active = false,
        country = "",
        flickrImages = emptyList(),
        rocketId = "",
        rocketName = "",
        description = "",
        engineCount = 0
    )

    val rocketList = listOf(rocket1, rocket2, rocket3)

    val activeRocketList = listOf(rocket1, rocket2)

}