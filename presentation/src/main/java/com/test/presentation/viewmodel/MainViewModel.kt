package com.test.presentation.viewmodel

import androidx.lifecycle.*
import com.test.presentation.model.Launch
import com.test.presentation.repository.PreferencesRepositoryContract
import com.test.presentation.resource.Resource

class MainViewModel(
    val preferencesRepositoryContract: PreferencesRepositoryContract
) : ViewModel(), LifecycleObserver {

    private val _isFirstLaunch: MediatorLiveData<Boolean> = MediatorLiveData()
    val isFirstLaunch: LiveData<Boolean>
        get() = _isFirstLaunch

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun getData() {
        _isFirstLaunch.value = preferencesRepositoryContract.getFirstLaunch()
    }

    //Set value in preferencesRepository
    fun setData() {
        preferencesRepositoryContract.setFirstLaunch()
    }

}