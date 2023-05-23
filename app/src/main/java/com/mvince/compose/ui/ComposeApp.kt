package com.mvince.compose.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mvince.compose.ui.Score.ScoreTableauScreen
import com.mvince.compose.ui.signUp.SignUpScreen
import com.mvince.compose.ui.details.DetailsScreen
import com.mvince.compose.ui.game.GameScreen
import com.mvince.compose.ui.signUp.SignUpScreen
import com.mvince.compose.ui.users.UsersScreen

@Composable
fun ComposeApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.SCORE
    ) {
        composable(Route.SIGN_UP) {
            SignUpScreen(navController)
        }
        composable(Route.GAME) {
            GameScreen()
        }
        composable(
            route = "${Route.DETAIL}/{${Argument.USERNAME}}",
            arguments = listOf(
                navArgument(Argument.USERNAME) {
                    type = NavType.StringType
                }
            ),
        ) {
            DetailsScreen(navController)
        }
        composable(Route.SCORE){
            ScoreTableauScreen()
        }
    }
}

object Route {
    const val USER = "user"
    const val DETAIL = "detail"
    const val SIGN_UP = "signUp"
    const val GAME = "game"
    const val SCORE = "tableauScore"
}

object Argument {
    const val USERNAME = "username"
}