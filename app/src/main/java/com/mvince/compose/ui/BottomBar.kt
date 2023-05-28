import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mvince.compose.ui.Route
import com.mvince.compose.ui.Score.ScoreTableauScreen
import com.mvince.compose.ui.game.GameScreen
import com.mvince.compose.ui.modifyUser.ModifyUserScreen
import com.mvince.compose.ui.rules.RulesScreen
import com.mvince.compose.ui.theme.JetpackComposeBoilerplateTheme
import com.mvince.compose.ui.users.UsersScreen

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(navController: NavHostController) {

    val appNavController = rememberNavController()
    val currentMenu = remember { mutableStateOf(Route.GAME) }

    JetpackComposeBoilerplateTheme() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "TrivialPoursuit",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                )
            },
            bottomBar = {
                NavigationBar() {
                    NavigationBarItem(
                        selected = currentMenu.value == Route.GAME,
                        onClick = {
                            currentMenu.value = Route.GAME
                            appNavController.navigate(Route.GAME)
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = com.mvince.compose.R.drawable.videogame),
                                contentDescription = "Trivial Pursuit"
                            )
                        },
                        label = { Text(text = "Trivial Pursuit") })
                    NavigationBarItem(
                        selected = currentMenu.value == Route.CLASSEMENT,
                        onClick = {
                            currentMenu.value = Route.CLASSEMENT
                            appNavController.navigate(Route.CLASSEMENT)
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = com.mvince.compose.R.drawable.ranking),
                                contentDescription = "Classement"
                            )
                        },
                        label = { Text(text = "Classement") })
                    NavigationBarItem(
                        selected = currentMenu.value == Route.RULES,
                        onClick = {
                            currentMenu.value = Route.RULES
                            navController.navigate(Route.RULES)
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = com.mvince.compose.R.drawable.rules),
                                contentDescription = "Règles"
                            )
                        },
                        label = { Text(text = "Règles") })
                    NavigationBarItem(selected = currentMenu.value == Route.USER,
                        onClick = {
                            currentMenu.value = Route.USER
                            appNavController.navigate(Route.USER)
                        }, icon = {
                            Icon(
                                painter = painterResource(id = com.mvince.compose.R.drawable.profile),
                                contentDescription = "Profil"
                            )
                        }, label = { Text(text = "Profil") })
                }
            }
        ) {
            NavHost(
                navController = appNavController,
                startDestination = Route.GAME
            ) {
                composable(Route.GAME) {
                    GameScreen(appNavController)
                }
                composable(Route.USER) {
                    UsersScreen(appNavController)
                }
                composable(Route.CLASSEMENT) {
                    ScoreTableauScreen(appNavController)
                }
                composable(Route.MODIFY_USER) {
                    ModifyUserScreen(appNavController)
                }
                composable(Route.RULES) {
                    RulesScreen(navController)
                }
            }
        }
    }
}