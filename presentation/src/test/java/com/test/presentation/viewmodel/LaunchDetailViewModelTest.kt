package com.test.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.test.presentation.model.Launch
import com.test.presentation.model.Rocket
import com.test.presentation.repository.LaunchRepositoryContract
import com.test.presentation.repository.RocketRepositoryContract
import com.test.presentation.resource.Resource
import com.test.presentation.util.LiveDataTestUtil
import org.junit.Assert
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class LaunchDetailViewModelTest {

    // Executes tasks in the Architecture Components in the same thread
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var launchDetailViewModel: LaunchDetailViewModel
    private val launchRepositoryContract = Mockito.mock(LaunchRepositoryContract::class.java)
    private val rocketRepositoryContract = Mockito.mock(RocketRepositoryContract::class.java)

    private val rocketID = "falcon9"

    @Before
    fun before() {
        launchDetailViewModel = LaunchDetailViewModel(
            launchRepositoryContract = launchRepositoryContract,
            rocketRepositoryContract = rocketRepositoryContract
        )

        launchDetailViewModel.setRocketID(rocketID = rocketID)

    }

    @Test
    fun processLaunchData() {

        val launchList = LaunchData.launchList

        val pair: Pair<Array<String>, FloatArray> = launchDetailViewModel.processLaunchData(launchList)

        //Test Array Strings
        val stringArray = pair.first
        Assert.assertEquals(stringArray.size, 2)
        Assert.assertEquals(stringArray[0], "2010")
        Assert.assertEquals(stringArray[1], "2019")

        //Test Float Array
        val floatArray = pair.second
        Assert.assertEquals(floatArray.size, 2)
        Assert.assertEquals(floatArray[0], 1.0F)
        Assert.assertEquals(floatArray[1], 2.0F)

    }

    @Test
    fun `getLaunchList`() {

        //Add a mock observer to our livedata so that we can verify the data is changed when we expect it to.
        val observer = mock(Observer::class.java) as Observer<Resource<List<Launch>>>
        launchDetailViewModel.launchList.observeForever(observer)

        val result: MutableLiveData<Resource<List<Launch>>> = MutableLiveData()
        result.value = Resource.success(data = LaunchData.launchList)
        `when`(
            launchRepositoryContract.getLaunches(rocketID = rocketID)
        ).thenReturn(
            result
        )

        //Test Function
        launchDetailViewModel.getLaunchList(rocketID = rocketID)
        Mockito.verify(launchRepositoryContract).getLaunches(rocketID = rocketID)

        // Then the Launch List is loaded.
        Assert.assertEquals(
            Resource.success(LaunchData.launchList),
            LiveDataTestUtil.getValue(launchDetailViewModel.launchList)
        )
    }

    @Test
    fun `getRocket`() {

        //Add a mock observer to our livedata so that we can verify the data is changed when we expect it to.
        val observer = mock(Observer::class.java) as Observer<Resource<Rocket>>
        launchDetailViewModel.rocket.observeForever(observer)


        val result: MutableLiveData<Resource<Rocket>> = MutableLiveData()
        result.value = Resource.success(data = RocketData.rocket1)
        `when`(
            rocketRepositoryContract.getRocket(rocketID = rocketID)
        ).thenReturn(
            result
        )

        //Test Function
        launchDetailViewModel.getRocket(rocketID = rocketID)
        Mockito.verify(rocketRepositoryContract).getRocket(rocketID = rocketID)

        // Then the Rocket is loaded.
        Assert.assertEquals(
            Resource.success(RocketData.rocket1),
            LiveDataTestUtil.getValue(launchDetailViewModel.rocket)
        )

    }

    @Test
    fun getData() {

        launchDetailViewModel.getData()

        Mockito.verify(launchRepositoryContract).getLaunches(rocketID = rocketID)
        Mockito.verify(rocketRepositoryContract).getRocket(rocketID = rocketID)
    }

    @Test
    fun refreshData() {

        launchDetailViewModel.refreshData()

        Mockito.verify(launchRepositoryContract).getLaunches(rocketID = rocketID)
        Mockito.verify(rocketRepositoryContract).getRocket(rocketID = rocketID)

        Assert.assertEquals(true, LiveDataTestUtil.getValue(launchDetailViewModel.swipeRefreshing))
    }
}