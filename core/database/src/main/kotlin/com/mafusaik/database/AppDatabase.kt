package com.mafusaik.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import model.CharacterEntity

@Database(
    entities = [CharacterEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun roomDao(): RickAndMortyDao
}