package com.test.presentation.resource

sealed class Resource<T> {

    //Define states
    data class Loading<T>(var data: T?) : Resource<T>()

    data class Success<T>(var data: T) : Resource<T>()

    data class Error<T>(val throwable: Throwable, var data: T) : Resource<T>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$throwable]"
            is Loading -> "Loading"
        }
    }

    companion object {

        fun <T> loading(data : T?): Resource<T> =
            Loading(data)

        fun <T> success(data: T): Resource<T> =
            Success(data)

        fun <T> error(throwable: Throwable, data: T): Resource<T> =
            Error(throwable, data)
    }

}