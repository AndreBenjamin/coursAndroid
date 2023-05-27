package com.mvince.compose.ui.modifyUser

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.mvince.compose.ui.signUp.checkEmailValidity


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

        // fetching local context
        val mContext = LocalContext.current

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = email,
                onValueChange = {
                    email = it;
                }, leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email Icon"
                    )
                },
                label = { Text(text = "Email") },
                placeholder = { Text(text = "Entrer votre Email") },
                modifier = Modifier.padding(top = 8.dp),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
            )
            TextField(
                value = pseudo,
                onValueChange = { pseudo = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Pseudo Icon"
                    )
                },
                label = { Text(text = "Pseudo") },
                placeholder = { Text(text = "Entrer votre Pseudo") },
                modifier = Modifier.padding(top = 8.dp),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                label = { Text(text = "Nouveau mot de passe") },
                placeholder = { Text(text = "Entrer votre nouveau MDP") },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,

                    )
            )
            Button(
                onClick = {
                    // val success = viewModel.modifyUserPswd(password)
                    if (password.length >= 8) {
                        viewModel.modifyUserPswd(password)
                    } else {
                        Toast.makeText(mContext, "Mot de passe trop petit, 8 caractères minimum", Toast.LENGTH_LONG).show()
                    }

                    currentUser.forEach {
                        val current = it as UserFirebase
                        if (pseudo != current.pseudo) {
                            viewModel.modifyPseudo(current.email, current.bestScore, current.score, pseudo, current.lastCo, current.signIn)
                        } else {
                            Toast.makeText(mContext, "Le pseudo est le même que l'ancien, aucun changement", Toast.LENGTH_SHORT).show()
                        }
                    }

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