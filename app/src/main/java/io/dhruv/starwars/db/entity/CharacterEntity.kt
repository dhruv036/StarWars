package io.dhruv.starwars.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.dhruv.starwars.constant.DateConverter
import io.dhruv.starwars.constant.ListConverter
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp


@Parcelize
@Entity
data class CharacterEntity(
    @PrimaryKey
    var id: Int,
    val name : String,
    val height: Int?,
    val mass: Int?,
    val gender: String?,
    val created: Long?,
    val edited: Long?,
    @TypeConverters(ListConverter::class)
    val filmList: String
) : Parcelable
