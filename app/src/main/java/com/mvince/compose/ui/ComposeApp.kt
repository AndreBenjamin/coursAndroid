package com.mvince.compose.ui

import BottomBar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mvince.compose.ui.Score.ScoreTableauScreen
import com.mvince.compose.ui.signUp.SignUpScreen
import com.mvince.compose.ui.details.DetailsScreen
import com.mvince.compose.ui.game.GameScreen
import com.mvince.compose.ui.modifyUser.ModifyUserScreen
import com.mvince.compose.ui.resetPassword.ResetPasswordScreen
import com.mvince.compose.ui.rules.RulesScreen
import com.mvince.compose.ui.signIn.SignInScreen
import com.mvince.compose.ui.users.UsersScreen
import com.mvince.compose.ui.welcomeScreen.WelcomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ComposeApp() {
    val navController = rememberNavController()
    val user = Firebase.auth.currentUser

    fun startRedirection(): String {
        if (user?.email != null){ // TODO BEN Retirer le == pour !=
            return Route.BOTTOM_BAR
        }
        return Route.WELCOME_SCREEN
    }

    NavHost(
        navController = navController,
        startDestination = startRedirection()
    ) {
        composable(Route.SIGN_UP) {
            SignUpScreen(navController)
        }
        composable(Route.SIGN_IN) {
            SignInScreen(navController)
        }
        composable(Route.GAME) {
            GameScreen(navController)
        }
        composable(Route.RESET_PASSWORD) {
            ResetPasswordScreen(navController)
        }
        composable(Route.WELCOME_SCREEN) {
            WelcomeScreen(navController)
        }
        composable(Route.RULES) {
            RulesScreen(navController)
        }
        composable(Route.USER) {
            UsersScreen(navController)
        }
        composable(Route.MODIFY_USER) {
            ModifyUserScreen(navController)
        }
        composable(Route.CLASSEMENT) {
            ScoreTableauScreen(navController)
        }
        composable(Route.BOTTOM_BAR) {
            BottomBar(navController)
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
    const val WELCOME_SCREEN = "welcome"
    const val RULES = "rules"
    const val MODIFY_USER = "modifyUser"
    const val CLASSEMENT = "classement"
    const val BOTTOM_BAR = "bottomBar"
}

object Argument {
    const val USERNAME = "username"
}