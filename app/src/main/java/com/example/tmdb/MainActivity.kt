package com.example.tmdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tmdb.presentation.discoverScreen.DiscoverScreen
import com.example.tmdb.presentation.discoverScreen.DiscoverScreenViewModel
import com.example.tmdb.presentation.util.Loading
import com.example.tmdb.ui.theme.TMDBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            val viewmodel: DiscoverScreenViewModel = viewModel()
            TMDBTheme {
                val isLoading=viewmodel.isLoading.collectAsStateWithLifecycle()
                if (isLoading.value) Loading()
                else DiscoverScreen(modifier = Modifier,viewmodel)
            }
        }
    }
}