package io.dhruv.starwars.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.dhruv.starwars.db.entity.CharacterRemoteKeyEntity

@Dao
interface RemoteKeyDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacterKey(character: List<CharacterRemoteKeyEntity>)

    @Query("SELECT * FROM CharacterRemoteKeyEntity WHERE id =:id")
    suspend fun getCharacterKeys(id: Int) : CharacterRemoteKeyEntity

    @Query("DELETE FROM CharacterRemoteKeyEntity")
    suspend fun deleteAllCharacterRemoteKeys()
}