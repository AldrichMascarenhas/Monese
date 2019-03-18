package com.test.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.data.local.model.CachedRocket
import io.reactivex.Completable

@Dao
interface CachedRocketDao {

    @Query("SELECT * FROM ROCKET WHERE rocketId = :rocketId")
    fun getRocket(rocketId: String): LiveData<CachedRocket>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRockets(rocketList: List<CachedRocket>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRocket(rocket: CachedRocket): Completable

    @Query("SELECT * FROM ROCKET")
    fun getRockets(): LiveData<List<CachedRocket>>
}

