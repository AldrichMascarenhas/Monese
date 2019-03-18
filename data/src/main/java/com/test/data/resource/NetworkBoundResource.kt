package com.test.data.resource

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.test.data.util.rx.SchedulerProvider
import com.test.presentation.resource.Resource
import com.test.presentation.util.extensions.tag
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 *
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */
abstract class NetworkBoundResource<ResultType : Any, RequestType : Any> constructor(
    private val schedulerProvider: SchedulerProvider
) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {

        // Send loading state to UI
        result.value = Resource.loading(null)

        val dbSource = this.loadFromDb()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData -> setValue(Resource.success(newData)) }
            }
        }
    }

    /**
     * Fetch the data from network and persist into DB and then
     * send it back to UI.
     */
    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {

        val apiResponse = createCall()

        /**
         * we re-attach dbSource as a new source, it will dispatch its latest value quickly
         * Loading must also have data and it is essential for the intended functionality,
         * otherwise starting a network fetch will completely invalidate the currently shown data.
         */
        result.addSource(dbSource) { newData ->
            setValue(Resource.loading(newData))
        }

        val intermediateLiveData = MutableLiveData<RequestType>()

        compositeDisposable.add(
            apiResponse
                .compose(schedulerProvider.getSchedulersForSingle())
                .subscribeBy(
                    onSuccess = {

                        Log.d(tag(), "Successfully made API Call")

                        //Reset Source
                        result.removeSource(dbSource)

                        //Set response to intermediateLiveData
                        intermediateLiveData.value = it

                    },
                    onError = {

                        Log.d(tag(), "API Call Failure")

                        result.removeSource(dbSource)

                        onFetchFailed()

                        result.addSource(dbSource) { newData ->
                            setValue(Resource.error(it, newData))
                        }
                    }
                )
        )

        //Once the successful call has been made, we want to save data to the database.
        result.addSource(intermediateLiveData) { response ->

            //Remove this source
            result.removeSource(intermediateLiveData)

            saveCallResult(processResponse(response))
                .compose(schedulerProvider.getSchedulersForCompletable())
                .subscribeBy(
                    onComplete = {

                        Log.d(tag(), "Successfully saved items to Database")


                        result.addSource(loadFromDb()) { newData ->
                            setValue(Resource.success(newData))
                        }

                    }, onError = {

                        Log.d(tag(), "Database saving failed : ${it.localizedMessage}")

                    })

            return@addSource

        }
    }

    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) result.value = newValue
    }

    //Override in concrete class if API Call failure is to be handled
    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    protected abstract fun processResponse(response: RequestType): ResultType

    protected abstract fun saveCallResult(item: ResultType): Completable

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun loadFromDb(): LiveData<ResultType>

    protected abstract fun createCall(): Single<RequestType>


}