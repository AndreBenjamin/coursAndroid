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
import com.mvince.compose.ui.Route

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(navHostController: NavHostController) {

// by default, the value is equal to 0, and remember will keep the value in memory

    val user = Firebase.auth.currentUser!!
    val viewModel = hiltViewModel<UsersViewModel>()

    // fetching local context
    val mContext = LocalContext.current

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
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //1

                if (user != null){
                    Text(text = "Pseudo : " + user.displayName, //faut mettre le pseudo fait par mika
                        style = MaterialTheme.typography.titleMedium)
                    Text(text = "email : " + user.email,
                        style = MaterialTheme.typography.titleMedium)
                    Text(text = "score : " , // faut mettre le score fait par Mika
                        style = MaterialTheme.typography.titleMedium)
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

                /* TextField(value = avatar, //image
                     onValueChange = { avatar = it },
                     visualTransformation = PasswordVisualTransformation(),
                     label = { Text(text = "Avatar") },
                     keyboardOptions = KeyboardOptions(
                         capitalization = KeyboardCapitalization.None,
                         autoCorrect = false,
                         keyboardType = KeyboardType.Text,
                         imeAction = ImeAction.Done,
                     )
                 )*/

                /* Column(
                     modifier = Modifier.fillMaxSize(),
                     verticalArrangement = Arrangement.Bottom
                 ) {
                    // Spacer(modifier = Modifier.weight(1f))
                     Row(Modifier.padding(16.dp)) {*/
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
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
                        viewModel.signOut()
                        Toast.makeText(mContext, "Vous êtes maintenant déconnecter", Toast.LENGTH_SHORT).show()
                        navHostController.navigate(Route.WELCOME_SCREEN)
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
        //bottomBar = { BottomBar(navHostController = navHostController) }
    )
}