package com.example.tmdb.ui

import android.icu.text.ListFormatter.Width
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PersonAddDisabled
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.tmdb.R
import com.example.tmdb.ui.theme.TMDBTheme

@Composable
fun DiscoverScreen(){
    var show by remember { mutableStateOf(false) }
    Column {
        FilterChipRow(viewModel = DiscoverScreenViewModel())
        Box {
            Image(painter = painterResource(R.drawable.another),
                contentDescription = null,
                modifier = Modifier)
//            Icon(imageVector = Icons.Default.ChevronLeft,
//                contentDescription = null,
//                modifier = Modifier.align(Alignment.CenterStart))
//            Icon(imageVector = Icons.Default.ChevronRight,
//                contentDescription = null,
//                modifier = Modifier.align(Alignment.CenterEnd))
            IconButton(
                onClick = {show=!show},
                modifier = Modifier.align(Alignment.TopEnd)) {
                Icon(
                    imageVector = if(show) Icons.Default.RemoveRedEye
                    else Icons.Default.PersonAddDisabled,
                    contentDescription = null,
                )
            }
            IconButton(
                onClick = {show=!show},
                modifier = Modifier.align(Alignment.BottomEnd)) {
                Icon(
                    imageVector = if(show) Icons.Default.Bookmark
                    else Icons.Default.BookmarkBorder,
                    contentDescription = null,
                )
            }
            if (show)
            Overview(Modifier.align(Alignment.BottomCenter))

        }
    }


}



@PreviewLightDark
@Composable
fun Overview(modifier: Modifier=Modifier){
    Column(modifier = modifier) {
        Text("Title", modifier = modifier.padding(start = 2.dp))
        Spacer(Modifier.height(4.dp))
        Text("Action & Adventure Animation Action & Adventure Animation",
            modifier = Modifier.padding(start = 2.dp))
        Spacer(Modifier.height(4.dp))
        Text("Synopsis...", fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(start = 2.dp))
        Spacer(Modifier.height(4.dp))
        Text("Isekai'd into another world, Haruka makes use of seemingly underpowered skills to live as a lone wolf.",
            modifier = Modifier.padding(start = 2.dp))
    }
}

@Composable
fun FilterChipRow(viewModel: DiscoverScreenViewModel){
    var chipState=viewModel.filterChipState.collectAsState()
    Row {
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
            label = { Text("Now Playing") }
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

@PreviewLightDark
@Composable
fun PreviewDiscoverScreen(){
    TMDBTheme {
        DiscoverScreen()
    }
}