package com.mafusaik.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import model.CharacterEntity

@Dao
interface RickAndMortyDao {
    @Query("SELECT * FROM characters")
    fun getAll(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM characters")
    fun getCharacters(): PagingSource<Int, CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<CharacterEntity>)

    @Query("SELECT COUNT(*) FROM characters")
    suspend fun getCount(): Int

    @Query("DELETE FROM characters")
    suspend fun clearAll()

    @Delete
    suspend fun delete(user: CharacterEntity)
}