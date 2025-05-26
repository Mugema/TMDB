package com.example.tmdb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.repository.TMDBRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    private val repository:TMDBRepositoryImpl
):ViewModel(){
    private var _state = MutableStateFlow(UiState())
    var state=_state.onStart { loadData() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            UiState()
        )


    private fun loadData(){
        viewModelScope.launch {
            _state.update { _state.value.copy(isLoading = false) }
        }

    }
}

data class UiState(
    val isLoading:Boolean = true
)