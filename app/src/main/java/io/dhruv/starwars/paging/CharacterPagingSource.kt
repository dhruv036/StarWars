package io.dhruv.starwars.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.dhruv.starwars.constant.CharacterToEntityConverter.convertCharacterListToEntityList
import io.dhruv.starwars.db.CharacterDB
import io.dhruv.starwars.db.entity.CharacterEntity
import io.dhruv.starwars.modal.ApiService
import java.lang.Exception
import javax.inject.Inject

class CharacterPagingSource @Inject constructor(val apiService: ApiService, val characterDB: CharacterDB) : PagingSource<Int, CharacterEntity>() {


    override fun getRefreshKey(state: PagingState<Int, CharacterEntity>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterEntity> {
        return try {
            val position = params.key ?: 1
            val response = apiService.getStarWarsChr(position)
            Log.e("response", "load: ${response.result}", )

            val modifyResponse = convertCharacterListToEntityList(response.result)

            characterDB.getCharacterDao().insertCharacter(modifyResponse)

            LoadResult.Page(
                data = modifyResponse,
                prevKey = if (position == 1) null else position-1,
                nextKey = if (response.next != null) position+1 else null
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }


}