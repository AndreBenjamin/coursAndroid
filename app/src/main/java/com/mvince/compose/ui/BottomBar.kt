import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mvince.compose.R
import com.mvince.compose.domain.UserFirebase
import com.mvince.compose.repository.UserFirebaseRepository
import com.mvince.compose.ui.Route
import com.mvince.compose.ui.Score.ScoreTableauScreen
import com.mvince.compose.ui.game.EndGameScreen
import com.mvince.compose.ui.game.GameScreen
import com.mvince.compose.ui.game.StartGameScreen
import com.mvince.compose.ui.modifyUser.ModifyUserScreen
import com.mvince.compose.ui.rules.RulesScreen
import com.mvince.compose.ui.signIn.SignInViewModel
import com.mvince.compose.ui.theme.JetpackComposeBoilerplateTheme
import com.mvince.compose.ui.users.UsersScreen
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(navController: NavHostController) {

    val viewModel = hiltViewModel<SignInViewModel>()
    val appNavController = rememberNavController()
    val currentMenu = remember { mutableStateOf(Route.GAME) }

    val currentUser = viewModel.currentUser.collectAsState().value
    val date = LocalDate.now().toString()

    fun startRedirection(): String {
        var route = Route.END_GAME
        currentUser.forEach {
            val current = it as UserFirebase
            var lastPlayed = current.lastPlayed
            if (lastPlayed != date){ // TODO BEN Retirer le == pour !=
                route = Route.START_GAME
            }
        }
        return route
    }

    fun getAvatar(): Int {
        var avatar = 2131099701
        currentUser.forEach {
            val current = it as UserFirebase
            var avatar = current.avatar
            return avatar
        }
        return avatar
    }

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
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                currentMenu.value = Route.USER
                                appNavController.navigate(Route.USER)
                            }
                        ) {
                            Image(
                                painter = painterResource(id = getAvatar()),
                                contentDescription = "",
                                modifier = Modifier.size(100.dp)
                            )
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar() {
                    NavigationBarItem(
                        selected = currentMenu.value == startRedirection(),
                        onClick = {
                            currentMenu.value = startRedirection()
                            appNavController.navigate(startRedirection())
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
                startDestination = startRedirection()
            ) {
                composable(Route.GAME) {
                    GameScreen(appNavController)
                }
                composable(Route.START_GAME) {
                    StartGameScreen(navController)
                }
                composable(Route.END_GAME) {
                    EndGameScreen(appNavController)
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