package com.test.data.repository

import androidx.lifecycle.LiveData
import com.test.data.local.SpaceXDatabase
import com.test.data.local.mapper.CachedRocketMapper
import com.test.data.local.model.CachedRocket
import com.test.data.network.SpaceXApiService
import com.test.data.network.response.RocketResponse
import com.test.data.resource.NetworkBoundResource
import com.test.data.util.rx.SchedulerProvider
import com.test.presentation.resource.Resource
import io.reactivex.Completable
import io.reactivex.Single

class RocketRepository(
    private val spaceXApiService: SpaceXApiService,
    private val spaceXDatabase: SpaceXDatabase,
    private val cachedRocketMapper: CachedRocketMapper,
    private val schedulerProvider: SchedulerProvider
) {

    fun getRockets(): LiveData<Resource<List<CachedRocket>>> {
        return object : NetworkBoundResource<List<CachedRocket>, RocketResponse>(schedulerProvider) {

            override fun processResponse(response: RocketResponse): List<CachedRocket> {

                val cachedRocketList = ArrayList<CachedRocket>()

                response.remoteRocketList.mapTo(cachedRocketList) {
                    return@mapTo cachedRocketMapper.mapToCached(it)
                }
                return cachedRocketList
            }

            override fun saveCallResult(item: List<CachedRocket>): Completable {
                return spaceXDatabase.cachedRocketDao().saveRockets(item)
            }

            override fun shouldFetch(data: List<CachedRocket>?): Boolean {
               return true
            }

            override fun loadFromDb(): LiveData<List<CachedRocket>> {
                return spaceXDatabase.cachedRocketDao().getRockets()
            }

            override fun createCall(): Single<RocketResponse> {
                return spaceXApiService.getAllRockets()
            }
        }.asLiveData()
    }

    fun getRocket(rocketID: String): LiveData<Resource<CachedRocket>> {
        return object : NetworkBoundResource<CachedRocket, RocketResponse>(schedulerProvider) {

            override fun processResponse(response: RocketResponse): CachedRocket {

                val cachedRocketList = ArrayList<CachedRocket>()

                response.remoteRocketList.mapTo(cachedRocketList) {
                    return@mapTo cachedRocketMapper.mapToCached(it)
                }
                return cachedRocketList.first()
            }

            override fun saveCallResult(item: CachedRocket): Completable {
                return spaceXDatabase.cachedRocketDao().saveRocket(item)
            }

            override fun shouldFetch(data: CachedRocket?): Boolean {
                return data?.let { false } ?: true
            }

            override fun loadFromDb(): LiveData<CachedRocket> {
                return spaceXDatabase.cachedRocketDao().getRocket(rocketId = rocketID)
            }

            override fun createCall(): Single<RocketResponse> {
                return spaceXApiService.getRocket(rocketID = rocketID)
            }
        }.asLiveData()
    }

}