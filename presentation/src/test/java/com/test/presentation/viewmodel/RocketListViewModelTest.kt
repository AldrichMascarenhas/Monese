package com.test.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.test.presentation.model.Rocket
import com.test.presentation.repository.RocketRepositoryContract
import com.test.presentation.resource.Resource
import com.test.presentation.util.LiveDataTestUtil
import org.junit.Assert
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.Mockito.*

class RocketListViewModelTest {

    // Executes tasks in the Architecture Components in the same thread
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var rocketListViewModel: RocketListViewModel
    private val rocketRepositoryContract = Mockito.mock(RocketRepositoryContract::class.java)

    @Before
    fun before() {
        rocketListViewModel = RocketListViewModel(
            rocketRepositoryContract = rocketRepositoryContract
        )
    }

    @Test
    fun `getRocketList`() {

        //Add a mock observer to our livedata so that we can verify the data is changed when we expect it to.
        val observer = Mockito.mock(Observer::class.java) as Observer<Resource<List<Rocket>>>
        rocketListViewModel.rocket.observeForever(observer)

        val result = MutableLiveData<Resource<List<Rocket>>>()
        result.value = Resource.success(RocketData.rocketList)
        `when`(
            rocketRepositoryContract.getRockets()
        ).thenReturn(
            result
        )

        //Test Function
        rocketListViewModel.getRocketList()
        Mockito.verify(rocketRepositoryContract).getRockets()

        Assert.assertEquals(Resource.success(RocketData.rocketList), rocketListViewModel.rocket.value)

    }

    @Test
    fun getData() {
        //Test Function
        rocketListViewModel.getData()

        Mockito.verify(rocketRepositoryContract).getRockets()
    }

    @Test
    fun refreshData() {

        //Add a mock observer to our livedata so that we can verify the data is changed when we expect it to.
        val observer = Mockito.mock(Observer::class.java) as Observer<Boolean>
        rocketListViewModel.swipeRefreshing.observeForever(observer)

        //Test Function
        rocketListViewModel.refreshData()

        verify(observer).onChanged(true)

        Mockito.verify(rocketRepositoryContract).getRockets()

        Assert.assertEquals(true, LiveDataTestUtil.getValue(rocketListViewModel.swipeRefreshing))
    }

    @Test
    fun `filterRocketList$presentation_debug`() {


        //Add a mock observer to our livedata so that we can verify the data is changed when we expect it to.
        val observer = Mockito.mock(Observer::class.java) as Observer<Resource<List<Rocket>>>
        rocketListViewModel.rocket.observeForever(observer)

        val result = MutableLiveData<Resource<List<Rocket>>>()
        result.value = Resource.success(RocketData.activeRocketList)

        `when`(
            rocketRepositoryContract.getRockets()
        ).thenReturn(
            result
        )

        //Test Function
        rocketListViewModel.filterRocketList()
        Mockito.verify(rocketRepositoryContract).getRockets()

        //Verify that the mock observer received data
        verify(observer).onChanged(Resource.success(RocketData.activeRocketList))

        Assert.assertEquals(Resource.success(RocketData.activeRocketList), rocketListViewModel.rocket.value)


    }
}