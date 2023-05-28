package com.mvince.compose.ui.users

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mvince.compose.R
import com.mvince.compose.domain.UserFirebase
import com.mvince.compose.ui.Route

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UsersScreen(navHostController: NavHostController) {

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
                Image(
                    painter = painterResource(id = current.avatar),
                    contentDescription = "Avatar",
                    modifier = Modifier.size(Dp(150F))
                )
                Text(text = "Pseudo : " + current.pseudo, //faut mettre le pseudo fait par mika
                    style = MaterialTheme.typography.titleMedium)
                Text(text = "email : " + current.email,
                    style = MaterialTheme.typography.titleMedium)
                Text(text = "score : "  + current.score, // faut mettre le score fait par Mika
                    style = MaterialTheme.typography.titleMedium)
                Text(text = "Inscription : "  + viewModel.formatDate(current.signIn), // faut mettre le score fait par Mika
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