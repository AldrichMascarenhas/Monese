package com.test.data.local.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Defining :
 * from + Type1 + to + Type2
 *
 * List :
 * ListOfType
 */
class PrimitiveTypeConverter {

    @TypeConverter
    fun fromListOfStringToString(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromStringToListOfString(string: String): List<String> {
        val gson = Gson()
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(string, listType)
    }

}