package com.example.tmdb.presentation.navigation

import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.tmdb.presentation.discoverScreen.DiscoverScreenRoot
import com.example.tmdb.presentation.login.LoginScreen
import com.example.tmdb.presentation.signUpScreen.SignUpScreen
import com.example.tmdb.presentation.watchLaterScreen.WatchLaterScreenRoot

@Composable
fun Navigation(
    navController: NavHostController
){
    NavHost(navController, startDestination = Routes.Landing){
        landing()
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


fun NavGraphBuilder.landing(){
    navigation<Routes.Landing>(startDestination = Routes.Discover){
        composable<Routes.Discover> {

            DiscoverScreenRoot( modifier = Modifier.safeContentPadding() )
        }
        composable<Routes.WatchLater> {
            WatchLaterScreenRoot( modifier = Modifier.safeContentPadding() )

        }
    }
}
