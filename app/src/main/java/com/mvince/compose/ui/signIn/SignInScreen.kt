package com.mvince.compose.ui.signIn

import android.annotation.SuppressLint
import android.os.Build
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.mvince.compose.ui.Route
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import kotlin.time.Duration.Companion.seconds

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navHostController: NavHostController) {
    val viewModel = hiltViewModel<SignInViewModel>()

    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

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
                if (showError) {
                    Text(
                        text = "Identifiant incorrect",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                TextField(
                    value = emailState.value,
                    onValueChange = {
                        emailState.value = it
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
                OutlinedTextField(
                    value = passwordState.value,
                    onValueChange = { passwordState.value = it },
                    visualTransformation = PasswordVisualTransformation(),
                    label = { Text(text = "Mot de passe") },
                    placeholder = { Text(text = "Entrer votre Mot De Passe") },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,

                        )
                )
                Button(
                    onClick = {
                        val email = emailState.value
                        val password = passwordState.value
                        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                            Toast.makeText(mContext, "Entrer un email valide", Toast.LENGTH_SHORT).show()
                            showError = true
                        } else if (email.isNotEmpty() and password.isEmpty()){
                            Toast.makeText(mContext, "champ mot de passe vide", Toast.LENGTH_SHORT).show()
                        } else if (email.isEmpty() and password.isEmpty()){
                            Toast.makeText(mContext, "Champs mot de passe et email vide", Toast.LENGTH_SHORT).show()
                        } else {
                            coroutineScope.launch {
                                val user = viewModel.signIn(email, password)
                                if (user?.email != "" && user?.email != null) {
                                    navHostController.navigate(Route.RULES)
                                } else {
                                    Toast.makeText(
                                        mContext,
                                        "L'utilisateur n'existe pas, ou le mot de passe ne correspond pas",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    showError = true
                                }
                            }
                        }
                    },
                    enabled = emailState.value.isNotEmpty() && passwordState.value.isNotEmpty(),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        text = "S'enregistrer",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
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
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { navHostController.navigate(Route.RESET_PASSWORD) },
                        modifier = Modifier
                            .width(150.dp)
                            .height(65.dp)
                    ) {
                        Text(
                            text = "Mot de passe oublier?",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                    Button(
                        onClick = { navHostController.navigate(Route.SIGN_UP) },
                        modifier = Modifier
                            .width(150.dp)
                            .height(65.dp)
                    ) {
                        Text(
                            text = "Créer un compte",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

    )
}