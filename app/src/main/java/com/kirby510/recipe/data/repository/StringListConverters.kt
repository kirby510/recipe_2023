package com.kirby510.recipe.data.repository

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringListConverters {
    var gson = Gson()

    @TypeConverter
    fun stringToStringList(data: String?): List<String?>? {
        if (data == null) {
            return emptyList<String>()
        }

        val listType = object : TypeToken<List<String?>?>() {}.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun stringListToString(someObjects: List<String?>?): String? {
        return gson.toJson(someObjects)
    }
}