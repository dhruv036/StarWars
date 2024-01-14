package io.dhruv.starwars.modal

import com.google.gson.annotations.SerializedName

data class AllCharacter(
    @SerializedName("count")
    val count :Int,
    @SerializedName("next")
    val next :String?,
    @SerializedName("previous")
    val previous :String?,
    @SerializedName("results")
    val result : List<Character>
)