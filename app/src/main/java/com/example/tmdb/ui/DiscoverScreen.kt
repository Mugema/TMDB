package com.example.tmdb.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.tmdb.ui.models.Movie
import com.example.tmdb.ui.util.GenreRow

@Composable
fun DiscoverScreen(modifier: Modifier=Modifier,viewModel: DiscoverScreenViewModel){
    val data = viewModel.movieData.collectAsStateWithLifecycle()
    var list= listOf<Movie>()
    Column {
        FilterChipRow(viewModel)
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(data.value){
                    movie ->  Discover(viewModel, movie)
            }
        }
    }
}

@Composable
fun Discover(viewModel: DiscoverScreenViewModel,movie: Movie){
    val iconState=viewModel.iconState.collectAsStateWithLifecycle()
    var sheet by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(8.dp))
        Box {
            AsyncImage(model = "https://image.tmdb.org/t/p/original/${movie.image}",
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth)
            IconButton(
                onClick = {viewModel.onEvent(Event.VISIBLE)},
                modifier = Modifier.align(Alignment.TopEnd)) {
                Icon(
                    imageVector = if(iconState.value.showOverview) Icons.Default.VisibilityOff
                    else Icons.Default.Visibility ,
                    contentDescription = null,
                )
            }
            IconButton(
                onClick = {viewModel.onEvent(Event.BOOKMARK)},
                modifier = Modifier.align(Alignment.TopStart)) {
                Icon(
                    imageVector = if(iconState.value.addBookmark) Icons.Default.Bookmark
                    else Icons.Default.BookmarkBorder,
                    contentDescription = null,
                )
            }
            if (iconState.value.showOverview)
            Overview(Modifier.align(Alignment.BottomCenter),movie)

        }
    }
}

@Composable
fun Overview(modifier: Modifier=Modifier,movie:Movie){
//    ModalBottomSheet(
//        onDismissRequest = {},
//    ) {
//        Text("Title", modifier = modifier.padding(start = 2.dp))
//        Spacer(Modifier.height(8.dp))
//        Text("Action & Adventure Animation Action & Adventure Animation",
//            modifier = Modifier.padding(start = 2.dp))
//        Spacer(Modifier.height(8.dp))
//        Text("Synopsis...", fontStyle = FontStyle.Italic,
//            modifier = Modifier.padding(start = 2.dp))
//        Spacer(Modifier.height(8.dp))
//        Text("Isekai'd into another world, Haruka makes use of seemingly underpowered skills to live as a lone wolf.",
//            modifier = Modifier.padding(start = 2.dp))
//    }

    OutlinedCard(modifier = modifier) {
        Spacer(Modifier.height(8.dp))
        Text(movie.title, modifier = modifier.padding(start = 8.dp, top = 2.dp))
        Spacer(Modifier.height(8.dp))
        Text("Rating: ${movie.rating}",modifier = modifier.padding(start = 8.dp, top = 2.dp))
        Spacer(Modifier.height(8.dp))
        Text("Release Date: ${movie.date}",modifier = modifier.padding(start = 8.dp, top = 2.dp))
        Spacer(Modifier.height(8.dp))
        GenreRow(movie.genre)
        Spacer(Modifier.height(8.dp))
        Text("Synopsis...", fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(start = 8.dp))
        Spacer(Modifier.height(8.dp))
        Text(movie.overview,modifier = Modifier.padding(start = 8.dp))
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun FilterChipRow(viewModel: DiscoverScreenViewModel){
    var chipState=viewModel.filterChipState.collectAsState()
    Row() {
        FilterChip(
            selected = chipState.value.nowPlaying,
            onClick = {
                viewModel.onChipStateChange("nowPlaying")
            },
            trailingIcon = {
                Icon(
                    imageVector = if (chipState.value.nowPlaying) Icons.Default.Check
                    else Icons.Default.Close,
                    contentDescription = null
                )
            },
            label = { Text("Now Playing") },
            modifier = Modifier.padding(start = 2.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        FilterChip(
            selected = chipState.value.upComing,
            onClick = {
                viewModel.onChipStateChange("upComing")
            },
            trailingIcon = {
                Icon(
                    imageVector = if (chipState.value.upComing) Icons.Default.Check
                    else Icons.Default.Close,
                    contentDescription = null
                )
            },
            label = { Text("Up Coming") }
        )
        Spacer(modifier = Modifier.width(4.dp))
        FilterChip(
            selected = chipState.value.topRated,
            onClick = {
                viewModel.onChipStateChange("topRated")
            },
            trailingIcon = {
                Icon(
                    imageVector = if (chipState.value.topRated) Icons.Default.Check
                    else Icons.Default.Close,
                    contentDescription = null
                )
            },
            label = { Text("Top Rated") }
        )
        Spacer(modifier = Modifier.width(4.dp))
    }
}



