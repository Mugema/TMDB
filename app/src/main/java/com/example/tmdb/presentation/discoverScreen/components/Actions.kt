package com.example.tmdb.presentation.discoverScreen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tmdb.presentation.Event
import com.example.tmdb.presentation.discoverScreen.DiscoverScreenViewModel

@Composable
fun Actions(viewModel: DiscoverScreenViewModel){
    val iconState=viewModel.iconState.collectAsStateWithLifecycle()
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = {viewModel.onEvent(Event.VISIBLE)},
            modifier = Modifier.align(Alignment.TopStart)) {
            Icon(
                imageVector = if(iconState.value.showOverview) Icons.Default.VisibilityOff
                else Icons.Default.Visibility ,
                contentDescription = null,
            )
        }
        IconButton(
            onClick = {viewModel.onEvent(Event.BOOKMARK)},
            modifier = Modifier.align(Alignment.BottomEnd)) {
            Icon(
                imageVector = if(iconState.value.addBookmark) Icons.Default.Bookmark
                else Icons.Default.BookmarkBorder,
                contentDescription = null,
            )
        }
    }
}