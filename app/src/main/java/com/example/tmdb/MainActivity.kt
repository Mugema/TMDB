package com.example.tmdb

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.tmdb.presentation.components.BottomBar
import com.example.tmdb.presentation.navigation.Navigation
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

                val state = viewmodel.state.collectAsStateWithLifecycle()
                val navController= rememberNavController()

                if (state.value.isLoading) Loading()

                else {
                    Scaffold(bottomBar ={ BottomBar(modifier = Modifier.background(Color.White).height(32.dp), navController=navController) } ){
                        Navigation( navController = navController) }
                }
            }
        }
    }
}


