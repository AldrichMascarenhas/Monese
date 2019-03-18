package com.test.data.repository

import androidx.lifecycle.LiveData
import com.test.data.local.SpaceXDatabase
import com.test.data.local.mapper.CachedLaunchMapper
import com.test.data.local.model.CachedLaunch
import com.test.data.network.SpaceXApiService
import com.test.data.network.response.LaunchResponse
import com.test.data.resource.NetworkBoundResource
import com.test.data.util.rx.SchedulerProvider
import com.test.presentation.resource.Resource
import io.reactivex.Completable
import io.reactivex.Single

class LaunchRepository(
    private val spaceXApiService: SpaceXApiService,
    private val spaceXDatabase: SpaceXDatabase,
    private val cachedLaunchMapper: CachedLaunchMapper,
    private val schedulerProvider: SchedulerProvider
) {

    fun getLaunches(rocketID: String): LiveData<Resource<List<CachedLaunch>>> {

        return object : NetworkBoundResource<List<CachedLaunch>, LaunchResponse>(schedulerProvider) {

            override fun processResponse(response: LaunchResponse): List<CachedLaunch> {
                val cachedRocketList = ArrayList<CachedLaunch>()

                response.remoteLaunchList.mapTo(cachedRocketList) {
                    return@mapTo cachedLaunchMapper.mapToCached(it)
                }
                return cachedRocketList
            }

            override fun saveCallResult(item: List<CachedLaunch>): Completable {
                return spaceXDatabase.cachedLaunchDao().saveLaunches(item)
            }

            override fun shouldFetch(data: List<CachedLaunch>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<CachedLaunch>> {
                return spaceXDatabase.cachedLaunchDao().getLaunches(rocketId = rocketID)
            }

            override fun createCall(): Single<LaunchResponse> {
                return spaceXApiService.getLaunch(rocketID = rocketID)
            }
        }.asLiveData()
    }

}