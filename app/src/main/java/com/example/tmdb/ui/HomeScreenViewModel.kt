package com.example.tmdb.ui

import androidx.lifecycle.ViewModel
import com.example.tmdb.data.repository.TMDBRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository:TMDBRepositoryImpl
):ViewModel() {
    private var _isLoading = MutableStateFlow(true)
    var isLoading=_isLoading.asStateFlow()

//    init {
//        viewModelScope.launch {
//            coroutineScope {
//                repository.addMovieGenre()
//                repository.addMovie()
//                repository.addMovieMGenreCrossRef()
//
//                delay(2000)
//            }
//            _isLoading.value=false
//        }
//    }
}

