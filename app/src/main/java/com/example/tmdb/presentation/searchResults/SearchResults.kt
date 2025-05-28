package com.example.tmdb.presentation.searchResults

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

    var isShowKnownFor by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        viewModel.updateSearchQuery(query)
    }

    Column(
        modifier=modifier
            .safeContentPadding()
            .padding(start = 4.dp, end = 4.dp)
            .fillMaxSize()
            .background(Color.White)

    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp, start = 4.dp)
        ) {
            ElevatedFilterChip(
                onClick = { viewModel.handleIntent(SearchResultIntents.ChipClicked(Showing.Movie)) },
                label = { Text("Movies") },
                selected = chipState.movie
            )
            ElevatedFilterChip(
                onClick = { viewModel.handleIntent(SearchResultIntents.ChipClicked(Showing.Tv)) },
                label = { Text("Tv") },
                selected = chipState.tv
            )
            ElevatedFilterChip(
                onClick = { viewModel.handleIntent(SearchResultIntents.ChipClicked(Showing.Actor)) },
                label = { Text("Actors") },
                selected = chipState.actor
            )
        }
        Text("Showing results for $query")

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
                    LoadingAnimation(modifier = modifier
                        .clip(RoundedCornerShape(10))
                        .weight(1f))
                }
            }
        }

        AnimatedContent(
            targetState = screenState.showing,
        ) { showingScreen ->
            when(showingScreen){
                Showing.Movie -> {
                    MovieResults(
                        modifier= Modifier.fillMaxSize(),
                        movieList = screenState.movies,
                        query=query
                    ) { toMovieDetails(it) }
                }
                Showing.Actor -> {
                    AnimatedContent(
                        targetState = isShowKnownFor
                    ) {
                        if (it){
                            MovieResults(
                                movieList = screenState.actors.filter { it.id == screenState.chosenActor }.map { it.knownFor }.flatten(),
                                query = screenState.searchQuery
                            ) { toMovieDetails(it)}
                        }
                        else{
                            ActorResult(
                                actorList = screenState.actors.sortedByDescending { it.popularity },
                                query = query,
                                showKnownFor = { isShowKnownFor = true}
                            ) { viewModel.handleIntent(it) }
                        }
                    }

                }
                Showing.Tv -> TODO()
            }
        }
    }
}

@Composable
fun Actor(
    modifier: Modifier = Modifier,
    actor: Actor,
    showKnownFor: (Boolean) -> Unit,
    handleIntent: (SearchResultIntents) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier=modifier
            .padding(top = 4.dp, bottom = 4.dp)
            .clickable {
                showKnownFor(true)
                handleIntent(SearchResultIntents.ActorClicked(actor.id))
            }
    ) {
        AnimatedVisibility(
            visible = actor.profilePath == "N/A"
        ) {
            Image(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier= Modifier.clip(CircleShape).size(64.dp)
            )
        }
        AsyncImage(
            model = "https://image.tmdb.org/t/p/original${actor.profilePath}",
            contentDescription = "Actors profile picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(64.dp)
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
    query: String,
    showKnownFor:(Boolean)->Unit,
    handleIntent:(SearchResultIntents) -> Unit
) {
    AnimatedVisibility(
        visible = actorList.isEmpty()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier=modifier.fillMaxSize()
        ){
            Text("No actors with the name $query", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        }
    }
    LazyColumn (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(actorList){ actor ->
            HorizontalDivider(thickness = 1.dp)
            Actor(
                modifier = modifier.fillMaxWidth(),
                actor = actor,
                showKnownFor = { showKnownFor(it) }
            ) { handleIntent(SearchResultIntents.ActorClicked(actor.id)) }
            Log.d("SearchResults","The actor passed the the Movie item $actor")
        }
    }

}

@Composable
fun MovieResults(
    modifier: Modifier = Modifier,
    movieList: List<Movie>,
    query: String,
    toMovieDetails:(movie:Movie)->Unit
) {
    AnimatedVisibility(
        visible = movieList.isEmpty()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier=modifier.fillMaxSize()
        ){
            Text("No movies found with the title $query", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        }
    }
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