package io.dhruv.starwars.modal

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.dhruv.starwars.db.CharacterDB
import io.dhruv.starwars.db.entity.CharacterEntity
import io.dhruv.starwars.paging.CharacterRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepo @Inject constructor(private val apiService: ApiService,private val characterDB: CharacterDB, private val remoteDataSource: RemoteDataSource) : BaseDataSource() {

    @OptIn(ExperimentalPagingApi::class)
    fun getCharadcter() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        remoteMediator = CharacterRemoteMediator(apiService,characterDB),
        pagingSourceFactory = { characterDB.getCharacterDao().getCharacters()}
    ).flow

    @OptIn(ExperimentalPagingApi::class)
    fun getSpecifCharadcter(something: String) = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { characterDB.getCharacterDao().getSpecifcCharacters(something) }
    ).flow

    suspend fun getFilm(movieId: Int) : Result<Film> =
        remoteDataSource.getfilmsDetails(movieId)

    fun getSortedCharacter(type: Int): Flow<PagingData<CharacterEntity>> {
        Log.e("00", "name: $type", )
        return Pager(
            config = PagingConfig(pageSize = 20, maxSize = 100),
            pagingSourceFactory = { when(type){
                0 ->{
                    characterDB.getCharacterDao().getSortedCharactersByName()
                }
                1 ->{
                    characterDB.getCharacterDao().getSortedCharactersByGender()
                }
                2 ->{
                    characterDB.getCharacterDao().getSortedCharactersByCreated()
                }
                3 ->{
                    characterDB.getCharacterDao().getSortedCharactersByUpdated()
                }
                else->{
                    characterDB.getCharacterDao().getSortedCharactersByUpdated()
                }
            } }
        ).flow
    }

}