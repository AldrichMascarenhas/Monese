import com.test.data.local.model.CachedRocket

object CachedRocketData{


    val rocket1 = CachedRocket(
        id = 1,
        active = true,
        country = "",
        flickrImages = emptyList(),
        rocketId = "",
        rocketName = "",
        description = "",
        engineCount = 0
    )

    val rocket2 = CachedRocket(
        id = 2,
        active = true,
        country = "",
        flickrImages = emptyList(),
        rocketId = "",
        rocketName = "",
        description = "",
        engineCount = 0
    )

    val rocket3 = CachedRocket(
        id = 3,
        active = false,
        country = "",
        flickrImages = emptyList(),
        rocketId = "",
        rocketName = "",
        description = "",
        engineCount = 0
    )

    val cachedRocketList = listOf(rocket1, rocket2, rocket3)

}