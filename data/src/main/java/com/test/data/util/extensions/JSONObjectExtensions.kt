package com.test.data.util.extensions

import com.google.gson.JsonObject

fun <T> JsonObject.defensiveTry(default: T, func: (JsonObject) -> T): T {
    return try {
        func(this)
    } catch (exception: Exception) {
        default
    }
}