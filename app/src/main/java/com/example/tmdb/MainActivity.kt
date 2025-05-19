package com.example.tmdb

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.tmdb.presentation.components.BottomBar
import com.example.tmdb.presentation.navigation.Navigation
import com.example.tmdb.presentation.navigation.NavigationViewModel
import com.example.tmdb.presentation.util.Loading
import com.example.tmdb.ui.theme.TMDBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TMDBTheme {
                val viewmodel:MainViewModel= viewModel()
                val navViewModel:NavigationViewModel = viewModel()

                val state = viewmodel.state.collectAsStateWithLifecycle()
                val navState = navViewModel.showBottomBar.collectAsStateWithLifecycle()
                val navController= rememberNavController()

                if (state.value.isLoading) Loading()

                else {
                    Scaffold(bottomBar = { if(navState.value) BottomAppBar(){ BottomBar(navController = navController) } }) {
                        Navigation( navController = navController) }
                }

                }
            }
        }
}


