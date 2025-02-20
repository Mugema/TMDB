package com.example.tmdb.presentation.discoverScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.example.tmdb.presentation.Event
import com.example.tmdb.presentation.discoverScreen.components.Actions
import com.example.tmdb.presentation.discoverScreen.components.Chip
import com.example.tmdb.presentation.models.Movie
import com.example.tmdb.presentation.util.GenreRow
import com.example.tmdb.ui.theme.TMDBTheme
import com.example.tmdb.ui.theme.primaryLight

@Composable
fun DiscoverScreen(
    modifier: Modifier = Modifier,
    discoverScreenState: DiscoverScreenState,
    onValueChange:(String)->Unit
){
    val pagerState= rememberPagerState(pageCount = {2})
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Movies","Series")
    var animate by remember { mutableStateOf(true) }

    LaunchedEffect(pagerState.currentPage,pagerState.isScrollInProgress) {
        if(!pagerState.isScrollInProgress)
            selectedTab=pagerState.currentPage
    }
    LaunchedEffect(selectedTab) {
        pagerState.animateScrollToPage(selectedTab)
    }

    Column(
        modifier.background(color = primaryLight)
    ) {
        Row {
            OutlinedTextField(
                value = discoverScreenState.searchQuery,
                onValueChange = {query->onValueChange(query)},
                placeholder={Text("Search")},
                trailingIcon = {
                    AnimatedVisibility(
                        visible = animate,
                        enter = EnterTransition.None,
                        exit = ExitTransition.None,
                        label = ""
                    ) {
                        if(discoverScreenState.searchQuery=="") {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null)
//                            animate = false
                        }
                        else
                            Icon(imageVector = Icons.Default.Clear,contentDescription = null)


                    }
                },
                modifier=Modifier
                    .padding(15.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.White)
                    .fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .drawWithContent {
                        drawCircle(
                            radius = 2f,
                            color = Color.Green
                        )
                        drawContent()
                    }
                    .background(Color.White)
            )

        }


        Column(
            Modifier
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Color.White)
        ){
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.height(54.dp)
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
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { index->
                if(index==0)
                    Discover(state = discoverScreenState)
                else
                {
                    // TODO
                }
            }

        }

    }
}

@Composable
fun Discover(modifier: Modifier=Modifier,state: DiscoverScreenState){
    Column {
        FilterChipRow(state.filterChipState)
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(state.movies){
                    movie ->  MovieItem( movie =  movie)
            }
        }
    }
}

@Composable
fun MovieItem(
    movie: Movie,
//    iconState: IconState,
){
    var imageResult by remember { mutableStateOf<Result<Painter>?>(null) }
    val painter = rememberAsyncImagePainter(
        model = "https://image.tmdb.org/t/p/original/${movie.image}",
        onSuccess = {
        },
        onError = {}
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier=Modifier.clickable {  }) {
            if (true){
                AsyncImage(model = "https://image.tmdb.org/t/p/original/${movie.image}",
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .blur(
                            radiusX = 100.dp,
                            radiusY = 50.dp,
                            edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(4.dp))
                        ),
                    contentScale = ContentScale.FillWidth)
                Overview(Modifier
                    .align(Alignment.BottomCenter)
                    .background(color = Color.Transparent),movie)
            }
            else{
                AsyncImage(model = "https://image.tmdb.org/t/p/original/${movie.image}",
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth)

            }

        }

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
        Text("Synopsis...", fontStyle = FontStyle.Italic,modifier = Modifier.padding(start = 8.dp))
        Spacer(Modifier.height(8.dp))
        Text(movie.overview,modifier = Modifier.padding(start = 8.dp))
        Spacer(Modifier.height(16.dp))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterChipRow(chipState: FilterChipState){
    FlowRow() {
        Chip(chipState.nowPlaying,"Now Playing") {
            Actions.onChipClicked("nowPlaying")
        }
        Chip(chipState.upComing,"UpComing") {
            Actions.onChipClicked("upComing")
        }
        Chip(chipState.topRated,"Top Rated") {
            Actions.onChipClicked("topRated")
        }
        Chip(chipState.popular,"Popular") {
            Actions.onChipClicked("popular")
        }
    }
}

val discoverScreen = DiscoverScreenState(
    searchQuery = "The Gorge",
    filterChipState = FilterChipState(),
    movies = listOf(
        Movie(
            title = "The Gorge",
            overview = "",
            image = TODO(),
            genre = listOf("Sci-fi","Horror"),
            rating = 4.9,
            date = "31.01.2020",
            bookmark = true,
            id = 2
        )
    )

)

@Preview
@Composable
private fun DiscoverScreenPreview() {
    TMDBTheme {
        DiscoverScreen(
            discoverScreenState = discoverScreen
        ) { }
    }

}





