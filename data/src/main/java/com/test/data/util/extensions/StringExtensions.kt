package com.test.data.util.extensions

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.formatToLocalDate(): String {

    val originalDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val convertedDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

    return try {
        val originalDate = originalDateFormat.parse(this)
        val convertedDate = convertedDateFormat.format(originalDate)
        convertedDate.toString()
    } catch (exception: ParseException) {
        ""
    }
}