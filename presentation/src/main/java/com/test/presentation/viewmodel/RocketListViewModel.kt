package com.test.presentation.viewmodel

import androidx.lifecycle.*
import com.test.presentation.model.Rocket
import com.test.presentation.repository.RocketRepositoryContract
import com.test.presentation.resource.Resource

class RocketListViewModel(
    private val rocketRepositoryContract: RocketRepositoryContract
) : ViewModel(), LifecycleObserver {

    private val _rocket: MediatorLiveData<Resource<List<Rocket>>> = MediatorLiveData()
    val rocket: LiveData<Resource<List<Rocket>>>
        get() = _rocket

    private val _swipeRefreshing: MediatorLiveData<Boolean> = MediatorLiveData()
    val swipeRefreshing: LiveData<Boolean>
        get() = _swipeRefreshing

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun getData() {
        getRocketList()
    }

    fun refreshData() {
        _swipeRefreshing.value = true
        getData()
    }

    internal fun getRocketList() {
        _rocket.addSource(rocketRepositoryContract.getRockets()) {
            _swipeRefreshing.value = false
            _rocket.value = it
        }
    }

    fun filterRocketList() {
        _rocket.addSource(rocketRepositoryContract.getRockets()) {
            when (it) {
                is Resource.Success -> {
                    val filteredList = it.data.filter {
                        it.active
                    }
                    _rocket.value = Resource.success(filteredList)
                }
            }
        }
    }

}