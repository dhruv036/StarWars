package io.dhruv.starwars.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class CharacterRemoteKeyEntity(
    @PrimaryKey
    val id: Int,
    val next: Int?,
    val previous: Int?
)