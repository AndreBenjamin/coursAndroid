package com.mvince.compose.ui.rules

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mvince.compose.domain.UserFirebase
import com.mvince.compose.ui.Route
import com.mvince.compose.ui.signIn.SignInViewModel
import java.time.LocalDate

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun RulesScreen(navHostController: NavHostController) {
    val viewModel = hiltViewModel<RulesViewModel>()

    val currentUser = viewModel.currentUser.collectAsState().value

    val date = LocalDate.now().toString()

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
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(
                        rememberScrollState()
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                currentUser.forEach {
                    val current = it as UserFirebase
                    Text(
                        text = "Bienvenue " + current?.pseudo,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = 20.dp).padding(bottom = 15.dp)
                    )
                }

                Text(
                    text = "Mécanique du jeu : Chaque jour, vous recevez un ensemble de questions à répondre. Les questions peuvent couvrir différents sujets, tels que la culture générale, les sports, l'histoire, la géographie, etc. Le jeu peut être basé sur un format de quiz à choix multiples, où vous devez sélectionner la bonne réponse parmi plusieurs options.\n" +
                            "\n" +
                            "Système de score : Chaque fois que vous répondez correctement à une question, vous obtenez des points. Le nombre de points peut varier en fonction de la difficulté de la question. Vous pouvez accumuler ces points au fil du temps pour augmenter votre score global.\n" +
                            "\n" +
                            "Classement et comparaison avec les amis : Le jeu propose un système de classement où vous pouvez voir votre position par rapport à vos amis."+
                            "\n" +
                            "En jouant à ce jeu, vous pouvez tester vos connaissances, améliorer votre score, et passer un bon moment tout en apprenant de nouvelles choses chaque jour.",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(bottom = 30.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(
                        rememberScrollState()
                    ),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        currentUser.forEach {
                            val current = it as UserFirebase
                            var lastCo = current.lastCo

                            if (lastCo != date){
                                val email = current.email
                                val lastPlayed = current.lastPlayed
                                val pseudo = current.pseudo
                                val bestScore = current.bestScore
                                val score = current.score
                                lastCo = date
                                val signIn = current.signIn
                                val avatar = current.avatar
                                viewModel.modifyUser(email, lastPlayed, bestScore, score, pseudo, lastCo, signIn, avatar)
                            }
                        }
                        navHostController.navigate(Route.BOTTOM_BAR)
                      },
                    modifier = Modifier.padding(start = 2.dp)
                ) {
                    Text(
                        text = "Suivant",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    )
}