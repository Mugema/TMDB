package com.example.tmdb.presentation.navigation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavigationViewModel: ViewModel() {

    private var _showBottomBar = MutableStateFlow(false)
    val showBottomBar = _showBottomBar.asStateFlow()

    fun onLoggedIn(){
        _showBottomBar.value=true
    }
}