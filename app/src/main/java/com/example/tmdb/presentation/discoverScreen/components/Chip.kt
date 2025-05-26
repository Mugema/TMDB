package com.example.tmdb.presentation.discoverScreen.components

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tmdb.domain.Category
import com.example.tmdb.presentation.discoverScreen.DiscoverScreenIntents
import com.example.tmdb.presentation.discoverScreen.FilterChipState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterChipRow(
    chipState: FilterChipState,
    onClick: (DiscoverScreenIntents.OnChipClicked) -> Unit
){
    LazyRow(modifier = Modifier.padding(start = 8.dp)) {
        item {
            Chip(
                chipState.nowPlaying,
                "Now Playing"
            ) { onClick(DiscoverScreenIntents.OnChipClicked(Category.NowPlaying)) }
            Chip(
                chipState.upComing,
                "UpComing"
            ) { onClick(DiscoverScreenIntents.OnChipClicked(Category.UpComing)) }
            Chip(
                chipState.topRated,
                "Top Rated"
            ) { onClick( DiscoverScreenIntents.OnChipClicked(Category.TopRated)) }
            Chip(
                chipState.popular,
                "Popular"
            ) { onClick( DiscoverScreenIntents.OnChipClicked(Category.Popular)) }
        }

    }
}


@Composable
fun Chip(
    state:Boolean,
    text:String,
    onClick:()->Unit
) {
    FilterChip(
        selected = state,
        onClick = { onClick() },
        trailingIcon = {
            Icon(
                imageVector = if (state) Icons.Default.Check
                else Icons.Default.Close,
                contentDescription = null
            )
        },
        label = { Text(text) },
        modifier = Modifier.padding(start = 2.dp)
    )
    Spacer(modifier = Modifier.width(4.dp))
}