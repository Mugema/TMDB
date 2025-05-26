package com.example.tmdb.presentation.searchResults

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.tmdb.presentation.components.LoadingAnimation
import com.example.tmdb.presentation.discoverScreen.components.MovieItem
import com.example.tmdb.presentation.models.Actor
import com.example.tmdb.presentation.models.Movie
import com.example.tmdb.ui.theme.TMDBTheme

@Composable
fun SearchResults(
    modifier: Modifier = Modifier,
    query:String = "",
    viewModel: SearchResultsViewModel = hiltViewModel<SearchResultsViewModel>(),
    toMovieDetails: (movie:Movie) -> Unit={}
) {
    val screenState by viewModel.searchResults.collectAsStateWithLifecycle()
    val chipState by viewModel.chipState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.updateSearchQuery(query)
    }

    Column(
        modifier=modifier
            .safeContentPadding()
            .background(Color.White)
            .fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp, start = 4.dp)
        ) {
            ElevatedFilterChip(
                onClick = { viewModel.chipIntent(Showing.Movie) },
                label = { Text("Movies") },
                selected = chipState.movie
            )
            ElevatedFilterChip(
                onClick = { viewModel.chipIntent(Showing.Tv) },
                label = { Text("Tv") },
                selected = chipState.tv
            )
            ElevatedFilterChip(
                onClick = { viewModel.chipIntent(Showing.Actor) },
                label = { Text("Actors") },
                selected = chipState.actor
            )
            Spacer(modifier= Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(8.dp)
                    .shadow(
                        8.dp,
                        RoundedCornerShape(20),
                        ambientColor = Color.Black,
                        spotColor = Color.White
                    )
                    .padding(4.dp)
            ){
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        }

        AnimatedVisibility(
            visible = screenState.isLoading,
            label = "Loading data"
        ) {
            LazyVerticalGrid(
                modifier = modifier.fillMaxSize(),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(12) { movie ->
                    LoadingAnimation(modifier = modifier.weight(1f))
                }
            }
        }

        AnimatedContent(
            targetState = screenState.showing
        ) { showingScreen ->
            when(showingScreen){
                Showing.Movie -> {
                    MovieResults(
                    modifier= Modifier.fillMaxSize(),
                    movieList = screenState.movies) { toMovieDetails(it) }
                }
                Showing.Actor -> {
                    ActorResult(actorList = screenState.actors)
                }
                Showing.Tv -> TODO()
            }
        }
    }
}

@Composable
fun Actor(modifier: Modifier = Modifier, actor: Actor) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier=modifier
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/original${actor.profilePath}",
            contentDescription = "Actors profile picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(CircleShape)
        )
        Column( verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("Name: ${actor.name}", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Text("Gender:${actor.gender}")
            Text("Occupation:${actor.knownForDepartment}")
        }
    }
}

@Composable
fun ActorResult(
    modifier: Modifier = Modifier,
    actorList: List<Actor>,
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(actorList){ actor ->
            Actor(modifier = modifier.fillMaxWidth(), actor = actor)
            Log.d("SearchResults","The actor passed the the Movie item $actor")
        }
    }

}

@Composable
fun MovieResults(
    modifier: Modifier = Modifier,
    movieList: List<Movie>,
    toMovieDetails:(Movie)->Unit

) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(movieList){ movie ->
            MovieItem(modifier=modifier ,movie =  movie){ toMovieDetails(it) }
            Log.d("SearchResults","The movie passed the the Movie item $movie")
        }
    }

}


@Preview
@Composable
private fun PreviewSearchResults() {
    TMDBTheme {
        SearchResults()
    }

}