package com.example.tmdb.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DiscoverScreenViewModel:ViewModel() {
    private var _filterChipState= MutableStateFlow(FilterChipState())
    val filterChipState=_filterChipState.asStateFlow()

    fun onChipStateChange(chip:String){
        when(chip){
            "nowPlaying"->{
                _filterChipState.value=_filterChipState.value.copy(
                    nowPlaying = !_filterChipState.value.nowPlaying)
            }
            "topRated"->{
                _filterChipState.value=_filterChipState.value.copy(
                    topRated = !_filterChipState.value.topRated)
            }
            "upComing"->{
                _filterChipState.value=_filterChipState.value.copy(
                    upComing = !_filterChipState.value.upComing)
            }

        }
    }

}

data class FilterChipState(
    val nowPlaying:Boolean=false,
    val topRated:Boolean=false,
    val upComing:Boolean=false,
)