package com.example.tmdb.presentation.bookMarkedScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.domain.repository.TMDBRepository
import com.example.tmdb.presentation.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookMarkedViewModel @Inject constructor(
    private val repository:TMDBRepository
):ViewModel() {
    private var _expanded = MutableStateFlow(false)
    var expanded= _expanded.asStateFlow()

    private var _bookmarked= MutableStateFlow(listOf<Movie>())
    val bookmarked=_bookmarked.asStateFlow()

    var currentMovie:Movie?=null


    fun onExpandedChange(movie: Movie?=null){
        _expanded.value=!_expanded.value
        currentMovie=movie
    }

    fun onDeleteClicked(id:Int){
        viewModelScope.launch {
            repository.bookMark(false,id)
        }
    }

     fun getMovieData(){
//        viewModelScope.launch {
//            _bookmarked.value=repository.getBookMarked().map{item->
//                item.toMovie()
//            }
//
//        }
    }

}