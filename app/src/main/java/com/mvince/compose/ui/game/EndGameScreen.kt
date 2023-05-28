package com.mvince.compose.ui.game

import android.os.Build
import android.text.Html
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mvince.compose.domain.UserFirebase
import com.mvince.compose.ui.Route
import com.mvince.compose.ui.signIn.SignInViewModel
import com.mvince.compose.ui.theme.*


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EndGameScreen(navHostController: NavHostController) {
    val viewModel = hiltViewModel<SignInViewModel>()
    val currentUser = viewModel.currentUser.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
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
            Text(
                text = "Vous avez déjà jouer aujourd'hui, en obtenant le score de " + current.score +"/100",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 20.dp).padding(bottom = 15.dp)
            )
            Text(
                text = "Revenez demain afin de découvrir de nouvelles questions",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 20.dp).padding(bottom = 15.dp)
            )
        }
    }
}