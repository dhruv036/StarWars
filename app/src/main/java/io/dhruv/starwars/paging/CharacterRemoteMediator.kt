package io.dhruv.starwars.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import io.dhruv.starwars.constant.CharacterToEntityConverter.convertCharacterListToEntityList
import io.dhruv.starwars.db.CharacterDB
import io.dhruv.starwars.db.entity.CharacterEntity
import io.dhruv.starwars.db.entity.CharacterRemoteKeyEntity
import io.dhruv.starwars.network.ApiService

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val apiService: ApiService,
    private val characterDB: CharacterDB
) : RemoteMediator<Int, CharacterEntity>() {

    val characterDao = characterDB.getCharacterDao()
    val remoteKeyDao = characterDB.getRemoteKeyDao()
    private var _counter =0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {

        return try {

            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.next?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.previous
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.next
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = apiService.getStarWarsChr(currentPage)
            val paginationEnd = (response.next == null)

            val prevPage = if (currentPage == 1) null else currentPage.minus(1)
            val nextPage = if (paginationEnd) null else currentPage.plus(1)


            var characters = convertCharacterListToEntityList(response.result)
            characters = characters.map {
                it.id = ++_counter
                it
            }

            characterDB.withTransaction {
                if (loadType == LoadType.REFRESH){
                    characterDao.deleteAllCharacter()
                    remoteKeyDao.deleteAllCharacterRemoteKeys()
                }

                characterDao.insertCharacter(characters)
                characters.map {
                    CharacterRemoteKeyEntity(
                        id = it.id,
                        next = nextPage,
                        previous = prevPage
                    )
                }.apply {
                    remoteKeyDao.insertAllCharacterKey(this)
                }
            }
            MediatorResult.Success(paginationEnd)

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, CharacterEntity>
    ): CharacterRemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeyDao.getCharacterKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, CharacterEntity>
    ): CharacterRemoteKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { character ->
                remoteKeyDao.getCharacterKeys(id = character.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, CharacterEntity>
    ): CharacterRemoteKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { character ->
                remoteKeyDao.getCharacterKeys(id = character.id)
            }
    }

}