package com.example.tmdb.presentation.bookMarkedScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tmdb.presentation.discoverScreen.components.MovieItem
import com.example.tmdb.presentation.models.Movie

@Composable
fun BookMarkedScreenRoot(
    modifier: Modifier = Modifier,
    toMovieDetails: (movie:Movie) -> Unit
) {
    val viewModel = hiltViewModel<BookMarkedViewModel>()

    BookMarkedScreen(
        modifier = modifier,
        state = viewModel.bookMarkedScreenState.collectAsStateWithLifecycle().value,
        toMovieDetails = toMovieDetails
        )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookMarkedScreen (
    modifier: Modifier,
    state: BookMarkedScreenState,
    toMovieDetails:(movie:Movie)->Unit
) {
    LazyVerticalGrid(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(top = WindowInsets.navigationBars.asPaddingValues().calculateTopPadding()+100.dp)
    ) {
        items(state.movieList){ movie ->
            MovieItem(modifier=modifier ,movie =  movie){ toMovieDetails(it) }
            Log.d("SearchResults","The movie passed the the Movie item $movie")
        }
    }
}
