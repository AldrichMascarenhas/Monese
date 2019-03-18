package com.test.presentation.util.extensions

fun Any.tag(): String {
    return this::class.java.simpleName
}