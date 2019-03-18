package com.test.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "launch")
data class CachedLaunch(
    @PrimaryKey val flightNumber: Int,
    val rocketId : String,
    val launchYear: Int,
    val missionName: String,
    val launchSuccess: Boolean,
    val launchDateUTC: String,
    val missionPatchImage: String
)