package com.example.tmdb.presentation.discoverScreen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.example.tmdb.presentation.models.Movie
import com.example.tmdb.presentation.util.GenreRow

@Composable
fun Overview(modifier: Modifier = Modifier, movie: Movie){
    OutlinedCard(
        modifier = modifier,
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.Transparent,
            contentColor = Color.Black)
    ) {
        Spacer(Modifier.height(8.dp))
        Text(movie.title, modifier = modifier.padding(start = 8.dp, top = 2.dp))
        Spacer(Modifier.height(8.dp))
        Text("Rating: ${movie.rating}",modifier = modifier.padding(start = 8.dp, top = 2.dp))
        Spacer(Modifier.height(8.dp))
        Text("Release Date: ${movie.date}",modifier = modifier.padding(start = 8.dp, top = 2.dp))
        Spacer(Modifier.height(8.dp))
        GenreRow(movie.genre)
        Spacer(Modifier.height(8.dp))
        Text("Synopsis...", fontStyle = FontStyle.Italic,modifier = Modifier.padding(start = 8.dp))
        Spacer(Modifier.height(8.dp))
        Text(movie.overview,modifier = Modifier.padding(start = 8.dp))
        Spacer(Modifier.height(16.dp))
    }
}

