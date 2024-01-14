package io.dhruv.starwars.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import io.dhruv.starwars.modal.CharacterRepo
import io.dhruv.starwars.modal.Film
import io.dhruv.starwars.modal.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailFragmentViewModel @Inject constructor(val repo: CharacterRepo) : ViewModel() {

    private val _movieDetails= MutableLiveData<Film?>()
    val moveDetail : MutableLiveData<Film?>
         get() = _movieDetails


    fun getFilms(movieId : Int) {
         viewModelScope.launch {
            _movieDetails.postValue(repo.getFilm(movieId).data)
         }
    }
}