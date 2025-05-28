package com.example.tmdb.presentation.bookMarkedScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tmdb.presentation.bookMarkedScreen.BookMarkedViewModel
import com.example.tmdb.presentation.models.Movie

@Composable
fun WatchItem(movie:Movie,viewModel: BookMarkedViewModel){
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically

        ){
            Text(movie.title,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 8.dp)

            )
            Spacer(Modifier.weight(1f))
            IconButton(
                onClick = {viewModel.onDeleteClicked(movie.id)
                          viewModel.getMovieData()},
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(imageVector = Icons.Filled.Delete,contentDescription = null,
                    tint = Color.Red)
            }
        }
        HorizontalDivider()
        Spacer(Modifier.height(4.dp))
        Text(movie.overview, maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 8.dp)
                .clickable { viewModel.onExpandedChange(movie) })

    }
}