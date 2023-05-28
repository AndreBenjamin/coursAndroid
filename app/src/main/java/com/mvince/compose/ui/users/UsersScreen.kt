package com.mvince.compose.ui.users

import BottomBar
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedContentScope.SlideDirection.Companion.End
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mvince.compose.domain.UserFirebase
import com.mvince.compose.ui.Route

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(navHostController: NavHostController) {

// by default, the value is equal to 0, and remember will keep the value in memory

    val user = Firebase.auth.currentUser
    val viewModel = hiltViewModel<UsersViewModel>()

    // fetching local context
    val mContext = LocalContext.current

    val currentUser = viewModel.currentUser.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //1

        currentUser.forEach {
            val current = it as UserFirebase
            if (user != null){
                Text(text = "Pseudo : " + current.pseudo, //faut mettre le pseudo fait par mika
                    style = MaterialTheme.typography.titleMedium)
                Text(text = "email : " + current.email,
                    style = MaterialTheme.typography.titleMedium)
                Text(text = "score : "  + current.score, // faut mettre le score fait par Mika
                    style = MaterialTheme.typography.titleMedium)
                Text(text = "Inscription : "  + current.signIn, // faut mettre le score fait par Mika
                    style = MaterialTheme.typography.titleMedium)
            }
        }


        Button(onClick = {
            navHostController.navigate(Route.MODIFY_USER)},
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(
                text = "Modifier l'utilisateur",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 50.dp),// TODO BEN Modifier affichage ici, c pa bo
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom,
        ) {
            Button(onClick = { viewModel.deleteUser() },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(
                    text = "Supprimer l'utilisateur",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Button(onClick = {
                viewModel.signOut(mContext)
                Toast.makeText(mContext, "Vous êtes maintenant déconnecter", Toast.LENGTH_SHORT).show()
            },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = "Deconnexion",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}