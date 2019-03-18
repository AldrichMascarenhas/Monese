package com.test.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.test.data.local.typeconverter.PrimitiveTypeConverter

@Entity(tableName = "rocket")
@TypeConverters(PrimitiveTypeConverter::class)
data class CachedRocket(
    @PrimaryKey val id: Int,
    val active: Boolean,
    val country: String,
    val flickrImages: List<String>,
    val rocketId: String,
    val rocketName: String,
    val description  :String,
    val engineCount: Int
)

