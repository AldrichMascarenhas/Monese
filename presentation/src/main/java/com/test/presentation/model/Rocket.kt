package com.test.presentation.model

data class Rocket(
    val id: Int,
    val active: Boolean,
    val country: String,
    val flickrImages: List<String>,
    val rocketId: String,
    val rocketName: String,
    val description: String,
    val engineCount: Int
)