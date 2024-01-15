package io.dhruv.starwars.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import io.dhruv.starwars.constant.ListConverter
import io.dhruv.starwars.db.entity.CharacterEntity
import io.dhruv.starwars.network.CharacterRepo
import io.dhruv.starwars.modal.Filter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class HomeFragmentViewModel @Inject constructor(val repo: CharacterRepo) : ViewModel() {

    var sequence: MutableLiveData<String> = MutableLiveData("")
    var isSearching: MutableLiveData<Boolean> = MutableLiveData(false)
    var isSorting: MutableLiveData<Boolean> = MutableLiveData(false)
    private var _filterName :  MutableLiveData<MutableList<Filter>> =  MutableLiveData(ListConverter.filterList)
    var updatedFilter : MutableLiveData<MutableList<Filter>>
    var field = MutableLiveData<Int>(-1)
    init {
        updatedFilter = _filterName
    }

    var list : Flow<PagingData<CharacterEntity>> = if (!isSearching.value!!){
        repo.getCharadcter().map { it }.cachedIn(viewModelScope)
    } else {
        emptyFlow()
    }

    fun getSpecificCharacter(): Flow<PagingData<CharacterEntity>> {
        return repo.getSpecifCharadcter(sequence.value!!).map { paging ->
            paging
        }.cachedIn(viewModelScope)
    }

    fun sort(type : Filter){
        updatedFilter.value!![type.id] = type
        if (type.isSelected){
            field.value = type.id
            isSorting.value = true
        }
    }

    fun getSortedCharacter(): Flow<PagingData<CharacterEntity>>{
     return repo.getSortedCharacter(field.value!!).map { paging ->
                paging
            }.cachedIn(viewModelScope)
        }
    }


    enum class FILTERTYPE(s: String) {
        NAME("name"),
        GENDER("gender"),
        CREATED("created"),
        UPDATED("edited")
    }