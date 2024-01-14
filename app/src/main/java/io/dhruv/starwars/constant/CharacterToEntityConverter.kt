package io.dhruv.starwars.constant

import io.dhruv.starwars.modal.Character
import io.dhruv.starwars.db.entity.CharacterEntity
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

object CharacterToEntityConverter {

     fun convertCharacterListToEntityList(characters: List<Character>): List<CharacterEntity> {
        return characters.map { convertModalToEntity(it) }
    }
     fun convertModalToEntity(character: Character): CharacterEntity {
         val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ", Locale.getDefault())

         return CharacterEntity(
            id = 0,
            name = character.name,
            height = character.height.toIntOrNull(),
            mass = character.mass.toIntOrNull(),
            gender = character.gender,
            filmList = ListConverter.fromArrayList(character.filmList),
            created = DateConverter.dateToTimestamp(character.created),
             edited = DateConverter.dateToTimestamp(character.edited)
        )
    }

     fun generateRandomIds() : Int{
        return Random.nextInt(0, 1000000)
    }
}