package com.test.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.data.local.model.CachedLaunch
import io.reactivex.Completable

@Dao
interface CachedLaunchDao {

    @Query("SELECT * FROM LAUNCH WHERE rocketId = :rocketId")
    fun getLaunches(rocketId: String): LiveData<List<CachedLaunch>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveLaunches(rocketList: List<CachedLaunch>): Completable
}