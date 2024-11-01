package com.example.tmdb.presentation.discoverScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.tmdb.presentation.Event
import com.example.tmdb.presentation.discoverScreen.components.Actions
import com.example.tmdb.presentation.discoverScreen.components.Chip
import com.example.tmdb.presentation.models.Movie
import com.example.tmdb.presentation.util.GenreRow

@Composable
fun DiscoverScreen(modifier: Modifier=Modifier,viewModel: DiscoverScreenViewModel){
    val pagerState= rememberPagerState(pageCount = {2})
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Movies","Series")

    LaunchedEffect(pagerState.currentPage,pagerState.isScrollInProgress) {
        if(!pagerState.isScrollInProgress)
            selectedTab=pagerState.currentPage
    }
    LaunchedEffect(selectedTab) {
        pagerState.animateScrollToPage(selectedTab)
    }

    Column {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ) {
            tabs.forEachIndexed{index,item->
                Tab(
                    index==selectedTab,
                    onClick = {selectedTab=index}
                ) {
                    Text(item,
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp)
                }
            }
        }
        HorizontalPager(state = pagerState,
            modifier = Modifier.fillMaxWidth()
                .weight(1f)) { index->
            if(index==0)
                Discover(viewModel = viewModel)
            else
            {
                Column(modifier.verticalScroll(rememberScrollState())) {
                    val ll=viewModel.unCategorisedMovies

                    Text("The size is ${ll.size}")
                    ll.forEach{popularMovie->
                        Overview(Modifier.background(Color.Transparent),movie = popularMovie)
                    }
                }
            }
        }
    }
}

@Composable
fun Discover(modifier: Modifier=Modifier,viewModel: DiscoverScreenViewModel){
    val data = viewModel.movie.collectAsStateWithLifecycle()
    Column {
        FilterChipRow(viewModel)
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(data.value){
                    movie ->  MovieItem(viewModel, movie)
            }
        }
    }
}

@Composable
fun MovieItem(viewModel: DiscoverScreenViewModel, movie: Movie){
    val iconState=viewModel.iconState.collectAsStateWithLifecycle()
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier=Modifier.clickable { viewModel.onEvent(Event.VISIBLE) }) {
            if (iconState.value.showOverview){
                AsyncImage(model = "https://image.tmdb.org/t/p/original/${movie.image}",
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                        .blur(
                            radiusX = 300.dp,
                            radiusY = 150.dp,
                            edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(4.dp))
                        ),
                    contentScale = ContentScale.FillWidth)
                Overview(Modifier.align(Alignment.BottomCenter).background(color = Color.Transparent),movie)
            }
            else{
                AsyncImage(model = "https://image.tmdb.org/t/p/original/${movie.image}",
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth)

            }

        }
        Actions(viewModel,movie.id)

    }
}

@Composable
fun Overview(modifier: Modifier=Modifier,movie: Movie){
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
        Text("Synopsis...", fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(start = 8.dp))
        Spacer(Modifier.height(8.dp))
        Text(movie.overview,modifier = Modifier.padding(start = 8.dp))
        Spacer(Modifier.height(16.dp))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterChipRow(viewModel: DiscoverScreenViewModel){
    val chipState=viewModel.filterChipState.collectAsState()
    FlowRow() {
        Chip(chipState.value.nowPlaying,"Now Playing") {
            viewModel.onChipStateChange("nowPlaying")
        }
        Chip(chipState.value.upComing,"UpComing") {
            viewModel.onChipStateChange("upComing")
        }
        Chip(chipState.value.topRated,"Top Rated") {
            viewModel.onChipStateChange("topRated")
        }
        Chip(chipState.value.popular,"Popular") {
            viewModel.onChipStateChange("popular")
        }
    }
}



