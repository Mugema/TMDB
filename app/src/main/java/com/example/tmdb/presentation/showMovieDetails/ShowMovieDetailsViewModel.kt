package com.example.tmdb.presentation.showMovieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.domain.useCases.BookMarkMovie
import com.example.tmdb.domain.useCases.GetSelectedMovie
import com.example.tmdb.presentation.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowMovieDetailsViewModel @Inject constructor(
    private val getMovieUseCase: GetSelectedMovie,
    private val bookMarkUseCase: BookMarkMovie
) : ViewModel(){

    private val _selectedMovie = MutableStateFlow(Movie())
    val selectedMovie = _selectedMovie.asStateFlow()

    fun getMovie(id:Int){
        viewModelScope.launch {
            getMovieUseCase.getMovieWithId(id).collect { movie->
                _selectedMovie.update { movie }
            }
        }
    }

    fun bookMark(bookMark: Boolean,id:Int){
        viewModelScope.launch {
            bookMarkUseCase.bookmarkMovie(bookMark = bookMark,id)
        }

    }
}