package com.example.tmdb.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.repository.TMDBRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository:TMDBRepositoryImpl
):ViewModel() {
    private var _isLoading = MutableStateFlow(true)
    var isLoading=_isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            coroutineScope {
                repository.addMovieGenre()
                repository.addMovie()
                repository.addMovieMGenreCrossRef()
            }
            _isLoading.value=false
        }
    }
}

