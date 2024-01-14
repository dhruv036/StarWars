package io.dhruv.starwars.constant

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

object DateConverter {
//    fun fromTimestamp(value: Long?): Date? {
//        return value?.let { Date(it) }
//    }

    @TypeConverter
    fun dateToTimestamp(time: String?): Long? {
        val dateString = time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

       return try {
            val date = dateFormat.parse(dateString)
            val timestamp = date.time
             timestamp
        } catch (e: Exception) {
           null
        }
    }
}