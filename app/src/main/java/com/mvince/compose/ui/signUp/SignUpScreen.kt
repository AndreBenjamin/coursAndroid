package com.mvince.compose.ui.signUp

import android.os.Build
import android.text.TextUtils
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mvince.compose.ui.signUp.SignUpViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun SignUpScreen(NavHostController: NavHostController) {
    val viewModel = hiltViewModel<SignUpViewModel>()

    // by default, the value is equal to 0, and remember will keep the value in memory
    var email by remember {
        mutableStateOf("")
    }
    var pseudo by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val error: Boolean? = true

    val authResource = viewModel.signupFlow.collectAsState()
    Box(
        contentAlignment = Alignment.Center
    ){
    Column(modifier = Modifier.fillMaxSize()) {
            if (error != null) {
                TextField(value = email, onValueChange = { email = it; error == !checkEmailValidity(email)  // Affiche une erreur si l'email n'est pas valide
                }, label = { Text(text = "Email") },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    isError = error // Affiche une erreur si l'email n'est pas valide
                )
            }
            TextField(value = pseudo,
                onValueChange = { pseudo = it },
                label = { Text(text = "Pseudo") },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                label = { Text(text = "Mot de passe") },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,

                    )
            )
            Button(
                onClick = { viewModel.signup(email, pseudo, password) },
                enabled = email.isNotEmpty() && pseudo.isNotEmpty() && password.isNotEmpty()
            ) {
                Text(
                    text = "S'inscrire",
                    style = MaterialTheme.typography.titleMedium
                )

            }
        }
    }
}

fun checkEmailValidity(email: String): Boolean{
    return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
}