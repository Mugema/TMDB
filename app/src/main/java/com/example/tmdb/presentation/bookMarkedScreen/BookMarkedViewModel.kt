package com.example.tmdb.presentation.bookMarkedScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.domain.repository.TMDBRepository
import com.example.tmdb.presentation.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookMarkedViewModel @Inject constructor(
    private val repository:TMDBRepository
):ViewModel() {

    private val _bookMarkedScreenState = MutableStateFlow(BookMarkedScreenState())
    val bookMarkedScreenState = _bookMarkedScreenState.onStart {
        getBookMarkedMovies()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        BookMarkedScreenState()

    )

    fun onDeleteClicked(id:Int){
        viewModelScope.launch {
            repository.bookMark(false,id)
        }
    }

     fun getBookMarkedMovies(){
         _bookMarkedScreenState.update { it.copy(isLoading = true) }
         viewModelScope.launch {
             repository.getBookMarkedMovie().collect { movieList ->
                 _bookMarkedScreenState.update { it.copy( movieList = movieList ) }
             }
         }
         _bookMarkedScreenState.update { it.copy(isLoading = false) }

    }

}

data class BookMarkedScreenState(
    val movieList:List<Movie> = emptyList<Movie>(),
    val isLoading: Boolean = true
)