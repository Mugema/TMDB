package com.example.tmdb.presentation.discoverScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tmdb.R
import com.example.tmdb.presentation.discoverScreen.components.FilterChipRow
import com.example.tmdb.presentation.discoverScreen.components.MovieItem
import com.example.tmdb.presentation.models.Movie
import com.example.tmdb.ui.theme.TMDBTheme

@Composable
fun DiscoverScreenRoot(
    modifier: Modifier = Modifier,
    toMovieDetails: (movie:Movie)-> Unit = {},
    toSearch: (query:String) -> Unit = {},
    toBookMarked:()->Unit ={}
) {
    val discoverScreenViewModel = hiltViewModel<DiscoverScreenViewModel>()

    val discoverScreenState = discoverScreenViewModel.discoverScreenState.collectAsStateWithLifecycle().value
    val chipState = discoverScreenViewModel.filterChipState.collectAsStateWithLifecycle().value

    DiscoverScreen(
        modifier = modifier,
        discoverScreenState=discoverScreenState,
        filterChipState = chipState,
        onIntent = discoverScreenViewModel::handleIntent,
        toMovieDetails = toMovieDetails,
        toSearch = toSearch,
        toBookMarked=toBookMarked
    )
}

@Composable
fun DiscoverScreen(
    modifier: Modifier = Modifier,
    discoverScreenState: DiscoverScreenState,
    filterChipState: FilterChipState,
    onIntent:(DiscoverScreenIntents)->Unit,
    toMovieDetails: (Movie) -> Unit,
    toSearch:(query: String) -> Unit,
    toBookMarked: () -> Unit
){
    var animate by remember { mutableStateOf(true) }
    var isShowSearchOnly by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.background(Color.White),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(R.drawable.aang),
                contentDescription = "Account Image",
                modifier= Modifier
                    .clip(CircleShape)
                    .size(64.dp)
                    .border(width = 1.dp, shape = CircleShape, color = Color.Green)
            )

            OutlinedTextField(
                value = discoverScreenState.searchQuery,
                onValueChange = { query-> onIntent(DiscoverScreenIntents.OnSearch(query)) },
                placeholder={ Text("Search") },
                shape = RoundedCornerShape(32.dp),
                modifier=Modifier
                    .background(Color.White)
                    .padding(15.dp)
                    .fillMaxWidth(),
                trailingIcon = {
                    AnimatedVisibility(
                        visible = animate,
                        enter = EnterTransition.None,
                        exit = ExitTransition.None,
                        label = ""
                    ) {
                        if(discoverScreenState.searchQuery=="") {
                            IconButton(onClick = { toSearch(discoverScreenState.searchQuery) }) {
                                Icon(imageVector = Icons.Default.Search, contentDescription = null,)
                            }
                        }
                        else {
                            IconButton(onClick = { onIntent(DiscoverScreenIntents.OnClearClicked) }) {
                                Icon(imageVector = Icons.Default.Clear,contentDescription = null)
                            }
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch =  {
                    toSearch(discoverScreenState.searchQuery)
                    onIntent(DiscoverScreenIntents.OnClearClicked)
                } )
            )
        }
        AnimatedVisibility(
            visible = animate,
            enter = fadeIn(spring(stiffness = Spring.StiffnessMedium, dampingRatio = Spring.DampingRatioHighBouncy)) + slideInHorizontally(),
            exit = fadeOut(spring(stiffness = Spring.StiffnessMedium, dampingRatio = Spring.DampingRatioHighBouncy)) + slideOutHorizontally()
        ) {
            if (!isShowSearchOnly){
                Column{
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        ElevatedFilterChip(
                            selected = discoverScreenState.screenTab.movies,
                            onClick={ onIntent(DiscoverScreenIntents.OnTabClicked(0)) },
                            label = { Text("Movies") }
                        )
                        ElevatedFilterChip(
                            selected = discoverScreenState.screenTab.series,
                            onClick={ onIntent(DiscoverScreenIntents.OnTabClicked(1)) },
                            label = { Text("Series") }
                        )
                        Spacer(Modifier.weight(1f))
                        IconButton(
                            onClick = { toBookMarked() },
                        ) {
                            Icon(imageVector = Icons.Default.Bookmarks, contentDescription = null)
                        }
                    }
                    FilterChipRow(filterChipState){ onIntent(it) }
                }
            }
        }

        AnimatedContent(
            targetState = discoverScreenState.isMovieTab,
        ) { state ->
            if (state) Discover(
                state = discoverScreenState,
                modifier = modifier,
                isShowSearchOnly = { isShowSearchOnly=it}
            ){
                toMovieDetails(it)
                onIntent(DiscoverScreenIntents.OnClearClicked) }
            else Box(modifier= Modifier.fillMaxSize()){Text("Works")}
        }
    }

}

@Composable
fun Discover(
    modifier: Modifier = Modifier,
    state: DiscoverScreenState,
    isShowSearchOnly:(bool:Boolean)->Unit,
    toMovieDetails: (Movie) -> Unit
){
    val lazyListState = rememberLazyGridState()
    if (lazyListState.isScrollInProgress){ isShowSearchOnly(true) }
    else isShowSearchOnly(false)
    Column(
        modifier=modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp, top = 4.dp)
    ) {
        LazyVerticalGrid(
            modifier = modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(state.movies){
                    movie ->  MovieItem(modifier=modifier.weight(1f) ,movie =  movie){ toMovieDetails(it) }
            }
        }
    }
}


@Preview
@Composable
private fun PreviewDiscoverScreen() {
    TMDBTheme {
//        DiscoverScreen()
    }

}




