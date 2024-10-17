package com.example.tmdb.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Task
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.motionEventSpy
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
    val loadingState= viewModel.isLoading.collectAsState().value
    if (loadingState) {
            Box(modifier = Modifier.fillMaxSize()){
                Image(painter = painterResource(R.drawable.logo), contentDescription = null,
                    modifier = Modifier.align(Alignment.Center))
                CircularProgressIndicator(modifier.width(128.dp)
                    .align(Alignment.Center))
            }
    } else {
        Scaffold(
            bottomBar = {
                BottomAppBar() { BottomBar() }
            },
            modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
        ) { padding ->
            Column(
                modifier.fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(padding)
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
    }
}
@Composable
fun BottomBar(modifier: Modifier=Modifier){
    val barItems = listOf(
        BarItem("Home", Icons.Default.Home,Icons.Outlined.Home,false),
        BarItem("Discover",Icons.Default.Explore,Icons.Outlined.Explore,false),
        BarItem("List",Icons.Default.Task,Icons.Outlined.Task,true),
        BarItem("Settings",Icons.Default.Settings,Icons.Outlined.Settings,true,9)
    )
    var selected by remember{ mutableIntStateOf(0) }

    NavigationBar(modifier=modifier) { barItems.forEachIndexed { index, barItem ->
        NavigationBarItem(
            selected = index==selected,
            onClick = {selected=index},
            icon = {
                BadgedBox(
                    badge = {
                        if(barItem.badge&&barItem.badgeCount!=0)
                            Badge(){Text(barItem.badgeCount.toString())}
                        else if(barItem.badge&&barItem.badgeCount==0)
                            Badge()
                    }
                ) {
                    Icon(imageVector = if(selected==index) barItem.selectedIcon
                    else barItem.unSelectedIcon,
                        contentDescription = null)
                }
            }
        )
    } }
}

data class BarItem(
    val label:String,
    val selectedIcon:ImageVector,
    val unSelectedIcon:ImageVector,
    val badge:Boolean,
    val badgeCount:Int=0,
)

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

@Composable
fun GenreRow(genreList:List<String>){
   LazyRow(modifier = Modifier.padding(4.dp)) {
       items(genreList){item->
           Box(
               modifier = Modifier.clip(RoundedCornerShape(100.dp))
                   .background(MaterialTheme.colorScheme.secondaryContainer)
                   .border(0.2.dp, Color.Black, RoundedCornerShape(100.dp))
           ) {
               Text(item, color = MaterialTheme.colorScheme.onTertiaryContainer,
                   modifier = Modifier.align(Alignment.Center).padding(2.dp))
           }
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

