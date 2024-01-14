package io.dhruv.starwars.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.dhruv.starwars.db.dao.CharacterDAO
import io.dhruv.starwars.db.dao.RemoteKeyDAO
import io.dhruv.starwars.db.entity.CharacterEntity
import io.dhruv.starwars.db.entity.CharacterRemoteKeyEntity


@Database(entities = [CharacterEntity::class,CharacterRemoteKeyEntity::class], version = 4)
abstract class CharacterDB : RoomDatabase() {

    abstract fun getCharacterDao() : CharacterDAO

    abstract fun getRemoteKeyDao() : RemoteKeyDAO
}