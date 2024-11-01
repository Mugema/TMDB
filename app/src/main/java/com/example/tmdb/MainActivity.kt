package com.example.tmdb

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Task
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tmdb.presentation.discoverScreen.DiscoverScreenViewModel
import com.example.tmdb.presentation.homeScreen.HomeScreenViewModel
import com.example.tmdb.presentation.navigation.Navigation
import com.example.tmdb.presentation.navigation.Routes
import com.example.tmdb.presentation.util.Loading
import com.example.tmdb.presentation.watchLaterScreen.WatchLaterViewModel
import com.example.tmdb.ui.theme.TMDBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            TMDBTheme {
                val viewmodel:MainViewModel= viewModel()
                val homeScreenViewModel: HomeScreenViewModel = viewModel()
                val discoverScreenViewModel: DiscoverScreenViewModel = viewModel()
                val watchLaterViewModel: WatchLaterViewModel = viewModel()
                val isLoading=viewmodel.isLoading.collectAsStateWithLifecycle()
                val navController= rememberNavController()

                if (isLoading.value) Loading()
                else {
                    Scaffold(bottomBar = { BottomAppBar(){ BottomBar(navController = navController) } }) {
                        Navigation(homeScreenViewModel,discoverScreenViewModel,watchLaterViewModel, navController = navController) }
                }

                }
            }
        }
}

@Composable
fun BottomBar(modifier: Modifier = Modifier, navController: NavController){
    val barItems = listOf(
        BarItem("Home", Icons.Default.Home, Icons.Outlined.Home,false),
        BarItem("Discover", Icons.Default.Explore, Icons.Outlined.Explore,false),
        BarItem("List", Icons.Default.Task, Icons.Outlined.Task,true),
        BarItem("Settings", Icons.Default.Settings, Icons.Outlined.Settings,true,9)
    )
    var selected by remember{ mutableIntStateOf(0) }

    NavigationBar(modifier=modifier) { barItems.forEachIndexed { index, barItem ->
        NavigationBarItem(
            selected = index==selected,
            onClick = {
                selected=index
                when(barItem.label){
                    "Discover"->{
                        navController.navigate(Routes.Discover)
                        selected=1
                    }
                    "Home"->{
                        navController.navigate(Routes.Home)
                        selected=0
                    }
                    "List"->{
                        navController.navigate(Routes.WatchLater)
                        selected=2
                    }
                } },
            icon = {
                BadgedBox(
                    badge = {
                        if(barItem.badge&&barItem.badgeCount!=0)
                            Badge(){ Text(barItem.badgeCount.toString()) }
                        else if(barItem.badge&&barItem.badgeCount==0)
                            Badge()
                    }
                ) {
                    Icon(imageVector = if(selected==index) barItem.selectedIcon
                    else barItem.unSelectedIcon,
                        contentDescription = null)
                }
            }
        ) }
    }
}

data class BarItem(
    val label:String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val badge:Boolean,
    val badgeCount:Int=0,
)

