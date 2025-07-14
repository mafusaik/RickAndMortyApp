package com.mafusaik.database

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromStringArray(value: List<String>?): String? {
        return value?.joinToString(separator = "|")
    }

    @TypeConverter
    fun toStringArray(value: String?): List<String>? {
        return value?.split("|")?.map { it.trim() }
    }
}