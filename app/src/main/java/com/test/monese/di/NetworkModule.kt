package com.test.monese.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.test.data.network.SpaceXApiService
import com.test.data.network.deserializer.LaunchDeserializer
import com.test.data.network.deserializer.RocketDeserializer
import com.test.data.network.response.LaunchResponse
import com.test.data.network.response.RocketResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Koin Module to provide dependenices for all Network Related Classes
 */
val remoteDataSourceModule = module {

    single { RocketDeserializer() }
    single { LaunchDeserializer() }

    single {
        createGson(
            rocketDeserializer = get(),
            launchDeserializer = get()
        )
    }

    single { createOkHttpClient() }

    single {
        createRetrofitService<SpaceXApiService>(
            okHttpClient = get(),
            url = getProperty("SPACEX_API_BASE_URL"),
            gson = get()
        )
    }
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

inline fun <reified T> createRetrofitService(okHttpClient: OkHttpClient, url: String, gson: Gson): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}

fun createGson(
    rocketDeserializer: RocketDeserializer,
    launchDeserializer: LaunchDeserializer
): Gson {
    return GsonBuilder()
        .registerTypeAdapter(RocketResponse::class.java, rocketDeserializer)
        .registerTypeAdapter(LaunchResponse::class.java, launchDeserializer)
        .create()
}