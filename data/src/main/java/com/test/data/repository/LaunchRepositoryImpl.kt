package com.test.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.test.data.local.model.CachedLaunch
import com.test.data.mapper.LaunchMapper
import com.test.presentation.model.Launch
import com.test.presentation.repository.LaunchRepositoryContract
import com.test.presentation.resource.Resource

class LaunchRepositoryImpl(
    private val launchRepository: LaunchRepository,
    private val launchMapper: LaunchMapper
) : LaunchRepositoryContract {

    override fun getLaunches(rocketID: String): LiveData<Resource<List<Launch>>> {
        return Transformations.switchMap(launchRepository.getLaunches(rocketID = rocketID)) {
            mapResource(it)
        }
    }

    internal fun mapResource(resource: Resource<List<CachedLaunch>>): LiveData<Resource<List<Launch>>> {

        val liveData: MutableLiveData<Resource<List<Launch>>> = MutableLiveData()

        when (resource) {
            is Resource.Success -> {

                val rocketList = ArrayList<Launch>()

                resource.data.mapTo(rocketList) {
                    return@mapTo launchMapper.mapFromEntity(it)
                }
                liveData.value = Resource.success(rocketList)
            }
            is Resource.Loading -> {

                resource.data?.let {
                    val rocketList = ArrayList<Launch>()

                    it.mapTo(rocketList) { cachedLaunch ->
                        return@mapTo launchMapper.mapFromEntity(cachedLaunch)
                    }

                    liveData.value = Resource.loading(data = rocketList)
                } ?: run {
                    liveData.value = Resource.loading(null)
                }
            }
            is Resource.Error -> {
                with(resource.data) {

                    val rocketList = ArrayList<Launch>()

                    mapTo(rocketList) {
                        return@mapTo launchMapper.mapFromEntity(it)
                    }
                    liveData.value = Resource.error(throwable = resource.throwable, data = rocketList)
                }
            }
        }
        return liveData

    }
}