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
import com.mvince.compose.ui.resetPassword.ResetPasswordScreen
import com.mvince.compose.ui.signIn.SignInScreen
import com.mvince.compose.ui.welcomeScreen.WelcomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ComposeApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.WELCOME_SCREEN
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
        composable(Route.RESET_PASSWORD) {
            ResetPasswordScreen(navController)
        }
        composable(Route.WELCOME_SCREEN) {
            WelcomeScreen(navController)
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
    const val RESET_PASSWORD = "resetPassword"
    const val WELCOME_SCREEN = "welcomeScreen"
}

object Argument {
    const val USERNAME = "username"
}