package io.dhruv.starwars.network

import io.dhruv.starwars.modal.AllCharacter
import io.dhruv.starwars.modal.Film
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("people/")
    suspend fun getStarWarsChr(@Query("page") pageId: Int) : AllCharacter

    @GET("films/{id}")
    suspend fun getFilmDetails(@Path("id") filmId: Int) : Response<Film>

}