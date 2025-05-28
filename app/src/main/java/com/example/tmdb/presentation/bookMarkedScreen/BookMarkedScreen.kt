package com.example.tmdb.presentation.bookMarkedScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tmdb.presentation.bookMarkedScreen.components.WatchItem
import com.example.tmdb.presentation.discoverScreen.components.Overview

@Composable
fun WatchLaterScreenRoot(modifier: Modifier = Modifier) {
    WatchLaterScreen( viewModel = hiltViewModel() )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchLaterScreen (viewModel: BookMarkedViewModel) {
    val expanded = viewModel.expanded.collectAsStateWithLifecycle()
    val bookmarked=viewModel.bookmarked.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Watch List") },
                navigationIcon = { Icon(Icons.Filled.ArrowBack, null) },
                actions = { Icon(Icons.Filled.Settings, null) }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)
            .also { if (expanded.value) it.blur(5.dp) }
        ) {
            if (expanded.value) {
                item {
                    Column (
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { viewModel.onExpandedChange() }
                    ){
                        viewModel.currentMovie?.let { Overview(modifier = Modifier, movie = it) }
                    }
                }
            }
            else{
                items(bookmarked.value){movie->
                    WatchItem(movie,viewModel)
                }
            }
        }
    }
}

