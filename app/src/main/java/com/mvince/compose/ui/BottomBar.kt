import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mvince.compose.ui.Route
import com.mvince.compose.ui.game.GameScreen
import com.mvince.compose.ui.theme.JetpackComposeBoilerplateTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(navHostController: NavHostController) {

    val appNavController = rememberNavController()
    val currentMenu = remember { mutableStateOf(Route.GAME) }

    JetpackComposeBoilerplateTheme() {
        Scaffold(
            bottomBar = {
                NavigationBar() {
                    NavigationBarItem(selected = currentMenu.value == Route.USER,
                        onClick = {
                            currentMenu.value = Route.USER
                            appNavController .navigate(Route.USER)
                        }, icon = {
                            Icon(
                                painter = painterResource(id = com.mvince.compose.R.drawable.profile),
                                contentDescription = "Profil"
                            )
                        }, label = { Text(text = "Profil") })
                    NavigationBarItem(
                        selected = currentMenu.value == Route.GAME,
                        onClick = {
                            currentMenu.value = Route.GAME
                            appNavController .navigate(Route.GAME)
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = com.mvince.compose.R.drawable.videogame),
                                contentDescription = "Trivial Pursuit"
                            )
                        },
                        label = { Text(text = "Trivial Pursuit") })
                    /*NavigationBarItem(
                        selected = selectedTab.value == Route.RANKING,
                        onClick = {
                            selectedTab.value = Route.RANKING
                            appNavController .navigate(Route.RANKING)
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = com.mvince.compose.R.drawable.trophy),
                                contentDescription = "Classement"
                            )
                        },
                        label = { Text(text = "Classement") })*/
                }
            }
        ) {
            NavHost(
                navController = appNavController,
                startDestination = Route.GAME
            ) {
                /*composable(Route.USER) {
                    UsersScreen(navController = navHostController)
                }*/
                composable(Route.GAME) {
                    GameScreen(navHostController = navHostController)
                }
                /*
                composable(Route.RANKING) {
                    RankingBody(navController = appNavController)
                }*/
            }
        }
    }
}
