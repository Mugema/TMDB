package com.example.tmdb.presentation.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tmdb.R
import com.example.tmdb.ui.theme.TMDBTheme

@Composable
fun HomeScreen(modifier:Modifier=Modifier,viewModel: HomeScreenViewModel) {
    Column(
        modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 2.dp, end = 2.dp)
        )
        Spacer(modifier.height(8.dp))
        TopRated()
        Spacer(modifier.height(8.dp))
        OutlinedCard(modifier = Modifier.fillMaxHeight()) {
            Image(
                painter = painterResource(R.drawable.anotherback),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Loner Life in Another World", fontWeight = FontWeight.Normal,
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Normal,
                )
                Icon(
                    imageVector = if (true) Icons.Filled.Bookmark
                    else Icons.Outlined.BookmarkBorder,
                    contentDescription = null,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier.height(4.dp))
            //HorizontalDivider(thickness = 1.dp)
            //GenreRow(genreList)
            //HorizontalDivider(thickness = 1.dp)
            Spacer(modifier.height(4.dp))
            Text("Synopsis...", fontStyle = FontStyle.Italic)
            Spacer(modifier.height(4.dp))
            Text("Isekai'd into another world, Haruka makes use of seemingly underpowered skills to live as a lone wolf.")
        }
    }
}
@Composable
fun TopRated(){
    LazyRow {
        items(10){item->
            Image(painter = painterResource(R.drawable.another) , contentDescription = null,
                modifier = Modifier.height(200.dp),
                contentScale = ContentScale.Inside)
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen(){
    TMDBTheme {
        //HomeScreen()
    }
}

