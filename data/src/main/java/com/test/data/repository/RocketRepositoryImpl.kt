package com.test.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.test.data.local.model.CachedRocket
import com.test.data.mapper.RocketMapper
import com.test.presentation.model.Rocket
import com.test.presentation.repository.RocketRepositoryContract
import com.test.presentation.resource.Resource

class RocketRepositoryImpl(
    private val rocketRepository: RocketRepository,
    private val rocketMapper: RocketMapper
) : RocketRepositoryContract {

    override fun getRockets(): LiveData<Resource<List<Rocket>>> {
        return Transformations.switchMap(rocketRepository.getRockets()) {
            mapCachedRocketListToRocketList(it)
        }
    }

    internal fun mapCachedRocketListToRocketList(resource: Resource<List<CachedRocket>>): LiveData<Resource<List<Rocket>>> {

        val liveData: MutableLiveData<Resource<List<Rocket>>> = MutableLiveData()

        when (resource) {
            is Resource.Success -> {

                val rocketList = ArrayList<Rocket>()

                resource.data.mapTo(rocketList) {
                    return@mapTo rocketMapper.mapFromEntity(it)
                }
                liveData.value = Resource.success(rocketList)
            }
            is Resource.Loading -> {

                resource.data?.let {
                    val rocketList = ArrayList<Rocket>()

                    it.mapTo(rocketList) { cachedRocket ->
                        return@mapTo rocketMapper.mapFromEntity(cachedRocket)
                    }

                    liveData.value = Resource.loading(data = rocketList)
                } ?: run {
                    liveData.value = Resource.loading(null)
                }
            }
            is Resource.Error -> {
                with(resource.data) {

                    val rocketList = ArrayList<Rocket>()

                    mapTo(rocketList) {
                        return@mapTo rocketMapper.mapFromEntity(it)
                    }
                    liveData.value = Resource.error(throwable = resource.throwable, data = rocketList)
                }
            }
        }
        return liveData
    }


    override fun getRocket(rocketID: String): LiveData<Resource<Rocket>> {

        return Transformations.switchMap(rocketRepository.getRocket(rocketID = rocketID)) {
            mapCachedRocketToRocket(it)
        }

    }


    internal fun mapCachedRocketToRocket(resource: Resource<CachedRocket>): LiveData<Resource<Rocket>> {

        val liveData: MutableLiveData<Resource<Rocket>> = MutableLiveData()

        when (resource) {
            is Resource.Success -> {
                val rocket = rocketMapper.mapFromEntity(resource.data)
                liveData.value = Resource.success(rocket)
            }
            is Resource.Loading -> {

                resource.data?.let {
                    val rocket = rocketMapper.mapFromEntity(it)
                    liveData.value = Resource.loading(data = rocket)
                } ?: run {
                    liveData.value = Resource.loading(null)
                }
            }
            is Resource.Error -> {
                with(resource.data) {
                    val rocket = rocketMapper.mapFromEntity(this)
                    liveData.value = Resource.error(throwable = resource.throwable, data = rocket)
                }
            }
        }
        return liveData
    }
}