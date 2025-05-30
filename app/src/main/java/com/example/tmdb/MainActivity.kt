package com.example.tmdb

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tmdb.presentation.navigation.Navigation
import com.example.tmdb.presentation.navigation.Routes
import com.example.tmdb.presentation.util.Loading
import com.example.tmdb.ui.theme.TMDBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TMDBTheme {
                val viewmodel:MainViewModel= viewModel()

                val state = viewmodel.state.collectAsStateWithLifecycle()
                val navController= rememberNavController()

                if (state.value.isLoading) Loading()

                else {
                    Scaffold(
                        topBar = {
                            val backStack by navController.currentBackStackEntryAsState()
                            val currentDestination=backStack?.destination

                            if (currentDestination?.hierarchy?.any { it.hasRoute(Routes.BookMarkedScreen::class) }==true){
                                TopBar { navController.navigateUp()  }
                            }
                        }
                    ){
                        Navigation( navController = navController) }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onBackClicked:()-> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text("BookMarked Movies") },
        navigationIcon = {
            IconButton(onClick = { onBackClicked() } ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            } },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors().copy(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
            ),
    )
}



