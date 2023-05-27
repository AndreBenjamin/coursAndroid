package com.mvince.compose.ui.resetPassword

import android.annotation.SuppressLint
import android.os.Build
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mvince.compose.ui.Route
import com.mvince.compose.ui.signUp.SignUpViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ResetPasswordScreen(navHostController: NavHostController) {
    val viewModel = hiltViewModel<ResetPasswordViewModel>()

    // by default, the value is equal to 0, and remember will keep the value in memory
    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }
    var showError by remember { mutableStateOf(false) }

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
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (showError) {
                Text(
                    text = "Email incorrect",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            TextField(
                value = email,
                onValueChange = { email = it },
                leadingIcon = {
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
                )
            )
            Button(
                onClick = {
                    if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        Toast.makeText(mContext, "Entrer un email valide", Toast.LENGTH_SHORT).show()
                        showError = true
                    } else {
                        viewModel.resetPassword(email)
                        Toast.makeText(mContext, "Un mail vient de vous être envoyer a l'adresse:" + email, Toast.LENGTH_SHORT).show()
                        navHostController.navigate(Route.WELCOME_SCREEN)
                    }
                  },
                enabled = email.isNotEmpty(),
                modifier = Modifier.padding(8.dp),
            ) {
                Text(
                    text = "Reinistialiser le mot de passe",
                    style = MaterialTheme.typography.titleMedium,
                )

            }
        }
    })

//
}

fun checkEmailValidity(email: String): Boolean {
    return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email)
        .matches();
}