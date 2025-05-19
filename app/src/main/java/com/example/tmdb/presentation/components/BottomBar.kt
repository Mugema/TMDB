package com.example.tmdb.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Task
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tmdb.presentation.models.BarItem
import com.example.tmdb.presentation.navigation.Routes

@Composable
fun BottomBar(modifier: Modifier = Modifier, navController: NavController){
    val barItems = listOf(
        BarItem("Discover", Icons.Default.Explore, Icons.Outlined.Explore,false, Routes.Discover),
        BarItem("List", Icons.Default.Task, Icons.Outlined.Task,true, Routes.WatchLater),
        BarItem("Settings", Icons.Default.Settings, Icons.Outlined.Settings,true, Routes.Settings,9)
    )
    val backStack by navController.currentBackStackEntryAsState()
    val currentDestination=backStack?.destination

    var selected by remember{ mutableIntStateOf(0) }

    NavigationBar(modifier=modifier) { barItems.forEachIndexed { index, barItem ->
        NavigationBarItem(
            selected = currentDestination?.hierarchy?.any { it.hasRoute(barItem.route::class) }==true,
            onClick = {
                navController.navigate(barItem.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                BadgedBox(
                    badge = {
                        if(barItem.badge&&barItem.badgeCount!=0)
                            Badge { Text(barItem.badgeCount.toString()) }
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