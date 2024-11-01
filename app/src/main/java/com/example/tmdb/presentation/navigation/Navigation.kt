package com.example.tmdb.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.tmdb.presentation.discoverScreen.DiscoverScreen
import com.example.tmdb.presentation.discoverScreen.DiscoverScreenViewModel
import com.example.tmdb.presentation.homeScreen.HomeScreen
import com.example.tmdb.presentation.homeScreen.HomeScreenViewModel
import com.example.tmdb.presentation.login.LoginScreen
import com.example.tmdb.presentation.signUpScreen.SignUpScreen
import com.example.tmdb.presentation.watchLaterScreen.WatchLaterScreen
import com.example.tmdb.presentation.watchLaterScreen.WatchLaterViewModel

@Composable
fun Navigation(
    homeScreenViewModel:HomeScreenViewModel,
    discoverScreenViewModel: DiscoverScreenViewModel,
    watchLaterViewModel: WatchLaterViewModel,
    navController: NavHostController
){
    NavHost(navController, startDestination = Routes.Authorization){
        authorization(navController)
        landing(homeScreenViewModel,discoverScreenViewModel,watchLaterViewModel)
    }

}

fun NavGraphBuilder.authorization(navController: NavController){
    navigation<Routes.Authorization>(startDestination = Routes.Login){
        composable<Routes.Login> {
            LoginScreen(toSignUp = {navController.navigate(Routes.SignUp)}) {
                navController.navigate(Routes.Home) }
        }
        composable<Routes.SignUp> {
            SignUpScreen(toLogin = { navController.navigate(Routes.Login) }) { navController.navigate(Routes.Home) }
        }

    }
}

@Composable
fun NextScreen(screen:String){
    val navController= rememberNavController()
    when(screen){
        "Home"-> navController.navigate(Routes.Home)
        "Discover"->navController.navigate(Routes.Discover)
        "List"->navController.navigate(Routes.WatchLater)
    }
}


fun NavGraphBuilder.landing(
    homeScreenViewModel:HomeScreenViewModel,
    discoverScreenViewModel: DiscoverScreenViewModel,
    watchLaterViewModel: WatchLaterViewModel
){
    navigation<Routes.Landing>(startDestination = Routes.Home){
        composable<Routes.Home> {
            HomeScreen(viewModel = homeScreenViewModel)
        }
        composable<Routes.Discover> {
            DiscoverScreen(viewModel = discoverScreenViewModel)
        }
        composable<Routes.WatchLater> {
            watchLaterViewModel.getMovieData()
            WatchLaterScreen(viewModel = watchLaterViewModel)
        }
    }
}
