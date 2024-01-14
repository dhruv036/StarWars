package io.dhruv.starwars.modal

import com.google.gson.annotations.SerializedName


data class Character(
    @SerializedName("name")
    val name: String,
    @SerializedName("mass")
    val mass:  String,
    @SerializedName("height")
    val height: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("films")
    val filmList: List<String>,
    @SerializedName("created")
    val created: String?,
    @SerializedName("edited")
    val edited: String?
)
