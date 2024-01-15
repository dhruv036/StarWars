package io.dhruv.starwars.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import io.dhruv.starwars.db.entity.CharacterEntity
import io.dhruv.starwars.network.CharacterRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterViewModel @Inject constructor(val repo: CharacterRepo) : ViewModel() {

    var sequence: MutableLiveData<String> = MutableLiveData("")
    var isSearcing: MutableLiveData<Boolean> = MutableLiveData(false)

    var list : Flow<PagingData<CharacterEntity>> = if (!isSearcing.value!!){
        repo.getCharadcter().map { it }.cachedIn(viewModelScope)
    } else {
        emptyFlow()
    }

    fun getSpecificCharacter(): Flow<PagingData<CharacterEntity>> {
        return repo.getSpecifCharadcter(sequence.value!!).map { paging ->
            paging
        }.cachedIn(viewModelScope)
    }
}