package com.test.data.network.deserializer

import com.google.gson.Gson
import com.test.data.di.remoteDataSourceModule
import com.test.data.network.response.LaunchResponse
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.Before
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject
import org.koin.test.KoinTest
import java.io.BufferedReader
import java.io.InputStreamReader

class LaunchDeserializerTest : KoinTest {
    private val gson: Gson by inject()

    @Before
    fun before() {
        StandAloneContext.startKoin(listOf(remoteDataSourceModule))
    }

    @After
    fun after() {
        StandAloneContext.stopKoin()
    }

    @Test
    fun `test Deserializer`() {

        //Load JSON from file
        val path = "LaunchResponseJson1.json"
        val target = InputStreamReader(this.javaClass.classLoader.getResourceAsStream(path))
        val bufferedReader = BufferedReader(target)

        val launchResponse = gson.fromJson(bufferedReader, LaunchResponse::class.java)

        Assert.assertEquals(launchResponse.remoteLaunchList.size, 84)

        val firstLaunch = launchResponse.remoteLaunchList.first()

        Assert.assertEquals(firstLaunch.flightNumber, 6)
        Assert.assertEquals(firstLaunch.rocketId, "falcon9")
        Assert.assertEquals(firstLaunch.launchYear, 2010)
        Assert.assertEquals(firstLaunch.missionName, "Falcon 9 Test Flight")
        Assert.assertEquals(firstLaunch.launchSuccess, true)
       // Assert.assertEquals(firstLaunch.launchDateUnix, 1275677100)
        Assert.assertEquals(firstLaunch.missionPatchImage, "https://images2.imgbox.com/d6/12/yxne8mMD_o.png")

    }
}