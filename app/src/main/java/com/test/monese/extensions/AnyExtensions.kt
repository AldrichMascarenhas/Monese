package com.test.monese.extensions

fun Any.tag(): String {
    return this.let {
        this::class.java.simpleName
    } ?: "MoneseApplication"
}