package io.dhruv.starwars.modal

import javax.inject.Inject

class RemoteDataSource @Inject constructor(val apiService: ApiService) : BaseDataSource() {

    suspend fun getfilmsDetails(filmId: Int)  = getResult{
        apiService.getFilmDetails(filmId)
    }

}