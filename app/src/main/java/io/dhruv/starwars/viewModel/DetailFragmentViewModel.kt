package io.dhruv.starwars.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.dhruv.starwars.network.CharacterRepo
import io.dhruv.starwars.modal.Film
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