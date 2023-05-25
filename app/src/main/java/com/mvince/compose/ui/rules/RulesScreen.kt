package com.mvince.compose.ui.rules

import android.annotation.SuppressLint
import android.os.Build
import android.text.TextUtils
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mvince.compose.domain.UserFirebase
import com.mvince.compose.ui.Route
import com.mvince.compose.ui.Score.ScoreTableauViewModel
import com.mvince.compose.ui.signUp.SignUpViewModel
import com.mvince.compose.ui.theme.Green700

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun RulesScreen(navHostController: NavHostController) {
    val viewModel = hiltViewModel<RulesViewModel>()

    val user = Firebase.auth.currentUser

    val currentUser = viewModel.currentUser.collectAsState().value


    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navHostController.navigate(Route.WELCOME_SCREEN) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                },
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
                    onClick = { navHostController.navigate(Route.BOTTOM_BAR) },
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

