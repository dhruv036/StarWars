package io.dhruv.starwars.constant

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.dhruv.starwars.modal.Filter
import java.lang.reflect.Type


object ListConverter {
    @TypeConverter
    fun fromString(value: String?): List<String> {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    val filterList = mutableListOf(
        Filter(0,"Name",false),
        Filter(1,"Gender",false),
        Filter(2,"Created",false),
        Filter(3,"Updated",false)
    )
}