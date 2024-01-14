package io.dhruv.starwars.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.dhruv.starwars.db.entity.CharacterEntity

@Dao
interface CharacterDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: List<CharacterEntity>)

    @Query("SELECT * FROM CharacterEntity WHERE name LIKE  '%' || :sequence || '%' ORDER BY id")
    fun getSpecifcCharacters(sequence : String) : PagingSource<Int,CharacterEntity>

    @Query("SELECT * FROM CharacterEntity")
    fun getCharacters() : PagingSource<Int,CharacterEntity>

    @Query("SELECT * FROM CharacterEntity ORDER BY name")
    fun getSortedCharactersByName() : PagingSource<Int,CharacterEntity>

    @Query("SELECT * FROM CharacterEntity ORDER BY gender")
    fun getSortedCharactersByGender() : PagingSource<Int,CharacterEntity>

    @Query("SELECT * FROM CharacterEntity ORDER BY created")
    fun getSortedCharactersByCreated() : PagingSource<Int,CharacterEntity>

    @Query("SELECT * FROM CharacterEntity ORDER BY edited")
    fun getSortedCharactersByUpdated() : PagingSource<Int,CharacterEntity>

    @Query("DELETE FROM CharacterEntity")
    suspend fun deleteAllCharacter()
}