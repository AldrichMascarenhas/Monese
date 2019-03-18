package com.test.presentation.viewmodel

import androidx.lifecycle.*
import com.test.presentation.model.Launch
import com.test.presentation.model.Rocket
import com.test.presentation.repository.LaunchRepositoryContract
import com.test.presentation.repository.RocketRepositoryContract
import com.test.presentation.resource.Resource

class LaunchDetailViewModel(
    private val launchRepositoryContract: LaunchRepositoryContract,
    private val rocketRepositoryContract: RocketRepositoryContract
) : ViewModel(), LifecycleObserver {

    private val _launchList: MediatorLiveData<Resource<List<Launch>>> = MediatorLiveData()
    val launchList: LiveData<Resource<List<Launch>>>
        get() = _launchList

    private val _rocket: MediatorLiveData<Resource<Rocket>> = MediatorLiveData()
    val rocket: LiveData<Resource<Rocket>>
        get() = _rocket


    private val _swipeRefreshing: MediatorLiveData<Boolean> = MediatorLiveData()
    val swipeRefreshing: LiveData<Boolean>
        get() = _swipeRefreshing

    private var _rocketID: String = ""

    fun setRocketID(rocketID: String) {
        _rocketID = rocketID
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun getData() {
        if (_rocketID.isNotEmpty()) {
            getLaunchList(_rocketID)
            getRocket(_rocketID)
        }
    }


    fun refreshData() {
        _swipeRefreshing.value = true
        getData()
    }


    internal fun getLaunchList(rocketID: String) {
        _launchList.addSource(
            launchRepositoryContract.getLaunches(rocketID = rocketID)
        ) {
            _launchList.value = it
            _swipeRefreshing.value = false
        }
    }

    internal fun getRocket(rocketID: String) {
        _rocket.addSource(rocketRepositoryContract.getRocket(rocketID = rocketID)) {
            _rocket.value = it
            _swipeRefreshing.value = false
        }
    }

    fun processLaunchData(launchList: List<Launch>): Pair<Array<String>, FloatArray> {

        val hashMap = mutableMapOf<String, MutableList<Launch>>()

        launchList.forEach { launch ->
            if (hashMap.containsKey(launch.launchYear.toString())) {
                val list = hashMap[launch.launchYear.toString()]
                list?.let {
                    list.add(launch)
                }
            } else {
                val list = mutableListOf<Launch>()
                list.add(launch)
                hashMap[launch.launchYear.toString()] = list
            }
        }

        //Sort HashMap by Keys
        val sortedHashMap = hashMap.toSortedMap()

        val mValues = ArrayList<Float>()
        sortedHashMap.values.mapTo(mValues) {
            it.size.toFloat()
        }

        return Pair(sortedHashMap.keys.toTypedArray(), mValues.toFloatArray())
    }

}