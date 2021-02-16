package com.papmobdev.data.database.typeconverters

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DiagnosticsTypeConverter {

    companion object {
        private const val pattern = "yyyy-MM-dd HH:mm:ss z"
        private var simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    }

    @TypeConverter
    fun toSimpleDateFormat(value: String): Date? = simpleDateFormat.parse(value)

    @TypeConverter
    fun fromSimpleDateTime(date: Date): String = simpleDateFormat.format(date)


}