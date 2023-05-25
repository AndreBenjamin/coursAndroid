package com.mvince.compose.ui.modifyUser

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mvince.compose.domain.UserFirebase
import com.mvince.compose.ui.Route


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyUserScreen(navHostController: NavHostController){
    val viewModel = hiltViewModel<ModifyUserViewModel>()
    val user = Firebase.auth.currentUser!!
    val currentUser = viewModel.currentUser.collectAsState().value
    currentUser.forEach {
        val current = it as UserFirebase
        var email by remember {
            mutableStateOf(current.email)
        }
        var pseudo by remember {
            mutableStateOf(current.pseudo)
        }

        var password by remember {
            mutableStateOf("")
        }
        /*var pseudo by remember {
            mutableStateOf(user.pseudo)
        }*/
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(value = email, onValueChange = { email = it }, label = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            TextField(
                value = pseudo,
                onValueChange = { pseudo = it },
                label = { Text(text = "Nouveau Pseudo") },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                )
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                label = { Text(text = "Nouveau mot de passe") },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                )
            )
            Button(
                onClick = {
                    viewModel.modifyUserPswd(password)
                    // val success = viewModel.modifyUserPswd(password)

                    navHostController.navigate(Route.USER)
                }
                // TODO Donia: Faire une condition si le psswd bien update: je redirigge vers la page user, sinon, j'affiche message erreur }
            ) {
                Text(
                    text = "Enregistrer",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}