package io.dhruv.starwars

import io.dhruv.starwars.constant.DateConverter
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

class Demo {
}

fun main() {
    val dateString = "2014-12-09T13:50:51.644000Z"
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")

    try {
        val date = dateFormat.parse(dateString)
        val timestamp = date.time
        println(timestamp)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}