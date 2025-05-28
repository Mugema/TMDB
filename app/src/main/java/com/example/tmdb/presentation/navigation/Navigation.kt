package com.example.tmdb.presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.tmdb.presentation.bookMarkedScreen.WatchLaterScreenRoot
import com.example.tmdb.presentation.discoverScreen.DiscoverScreenRoot
import com.example.tmdb.presentation.login.LoginScreen
import com.example.tmdb.presentation.navigation.Routes.MovieDetailsScreen
import com.example.tmdb.presentation.searchResults.SearchResults
import com.example.tmdb.presentation.showMovieDetails.MovieDetails
import com.example.tmdb.presentation.signUpScreen.SignUpScreen

@Composable
fun Navigation(
    navController: NavHostController
){
    NavHost(navController, startDestination = Routes.Landing){
        landing(navController)
    }
}

fun NavGraphBuilder.landing(navController: NavController){
    navigation<Routes.Landing>(startDestination = Routes.Discover){
        composable<Routes.Discover> {
            DiscoverScreenRoot(
                modifier = Modifier.safeContentPadding(),
                toMovieDetails = { navController.navigate( MovieDetailsScreen(movieId = it.id) ) },
                toSearch = { navController.navigate(Routes.SearchResults(it)) },
                toBookMarked = { navController.navigate(Routes.BookMarkedScreen)}
            )
        }
        composable<Routes.BookMarkedScreen> {
            WatchLaterScreenRoot( modifier = Modifier.safeContentPadding() )
        }

        composable<MovieDetailsScreen> { backStack ->
            val movie = backStack.toRoute<MovieDetailsScreen>()
            Log.d("Navigation", "MovieDetailScreen ${movie.movieId}")
            MovieDetails(
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars),
                movieId = movie.movieId)
        }

        composable<Routes.SearchResults> { backStack ->
            val query = backStack.toRoute<Routes.SearchResults>().query
            SearchResults(
                modifier = Modifier.safeContentPadding(),
                query=query
            ) { navController.navigate( MovieDetailsScreen(movieId = it.id) )
            Log.d("Navigation", "Movie sent by searchResult: $it")}
        }
    }
}


fun NavGraphBuilder.authorization(navController: NavController,onAuthorised:()->Unit){
    navigation<Routes.Authorization>(startDestination = Routes.Login){
        composable<Routes.Login> {
            LoginScreen(toSignUp = {navController.navigate(Routes.SignUp)}) {
                navController.navigate(Routes.Discover)
//                navController.popBackStack()
                onAuthorised()
            }
        }
        composable<Routes.SignUp> {
            SignUpScreen(toLogin = { navController.navigate(Routes.Login) }) { navController.navigate(Routes.Discover) }
        }

    }
}
