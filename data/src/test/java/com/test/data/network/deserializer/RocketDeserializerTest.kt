package com.test.data.network.deserializer

import com.google.gson.Gson
import com.test.data.di.remoteDataSourceModule
import com.test.data.network.response.RocketResponse
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject
import org.koin.test.KoinTest
import java.io.BufferedReader
import java.io.InputStreamReader

class RocketDeserializerTest : KoinTest {

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
    fun `test Deserializer`(){

        //Load JSON from file
        val path = "RocketResponseJson1.json"
        val target = InputStreamReader(this.javaClass.classLoader.getResourceAsStream(path))
        val bufferedReader = BufferedReader(target)

        val rocketResponse = gson.fromJson(bufferedReader, RocketResponse::class.java)

        Assert.assertEquals(rocketResponse.remoteRocketList.size, 4)

        val firstRocket = rocketResponse.remoteRocketList.first()

        Assert.assertEquals(firstRocket.id, 1)
        Assert.assertEquals(firstRocket.active, false)
        Assert.assertEquals(firstRocket.country, "Republic of the Marshall Islands")
        Assert.assertEquals(firstRocket.flickrImages.size, 2)
        Assert.assertEquals(firstRocket.rocketId, "falcon1")
        Assert.assertEquals(firstRocket.rocketName, "Falcon 1")
        Assert.assertEquals(firstRocket.engineCount, 1)

    }

}