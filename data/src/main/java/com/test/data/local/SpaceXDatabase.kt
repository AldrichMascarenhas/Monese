package com.test.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.data.local.dao.CachedLaunchDao
import com.test.data.local.dao.CachedRocketDao
import com.test.data.local.model.CachedLaunch
import com.test.data.local.model.CachedRocket

@Database(
    entities = [
        CachedLaunch::class,
        CachedRocket::class
    ],
    version = 3
)
abstract class SpaceXDatabase : RoomDatabase() {

    abstract fun cachedRocketDao(): CachedRocketDao

    abstract fun cachedLaunchDao(): CachedLaunchDao
}