package com.mvince.compose.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mvince.compose.ui.signUp.SignUpScreen
import com.mvince.compose.ui.details.DetailsScreen
import com.mvince.compose.ui.game.GameScreen
import com.mvince.compose.ui.signIn.SignInScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ComposeApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.SIGN_IN
    ) {
        composable(Route.SIGN_UP) {
            SignUpScreen(navController)
        }
        composable(Route.SIGN_IN) {
            SignInScreen(navController)
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
    }
}

object Route {
    const val USER = "user"
    const val DETAIL = "detail"
    const val SIGN_UP = "signUp"
    const val SIGN_IN = "signIn"
    const val GAME = "game"
}

object Argument {
    const val USERNAME = "username"
}