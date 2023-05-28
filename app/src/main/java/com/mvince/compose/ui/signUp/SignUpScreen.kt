package com.mvince.compose.ui.signUp

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
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mvince.compose.ui.Route
import com.mvince.compose.ui.signUp.SignUpViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun SignUpScreen(navHostController: NavHostController) {
    val viewModel = hiltViewModel<SignUpViewModel>()

    val emailState = remember { mutableStateOf("") }
    val pseudoState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    val error: Boolean? = true

    val authResource = viewModel.signupFlow.collectAsState()
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (showError) {
                    Text(
                        text = "Compte déjà existant",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                if (error != null) {
                    TextField(
                        value = emailState.value,
                        onValueChange = {
                            emailState.value = it;
                            error == !checkEmailValidity(emailState.value)  // Affiche une erreur si l'email n'est pas valide
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
                        isError = error // Affiche une erreur si l'email n'est pas valide
                    )
                }
                TextField(
                    value = pseudoState.value,
                    onValueChange = { pseudoState.value = it },
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
                        val pseudo = pseudoState.value
                        val password = passwordState.value
                        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                            Toast.makeText(mContext, "Email invalide", Toast.LENGTH_SHORT).show()
                            showError = true
                        } else if (password.length < 8){
                            Toast.makeText(mContext, "Le mot de passe a besoin de 8 caractères minimum", Toast.LENGTH_SHORT).show()
                            showError = true
                        } else {
                            coroutineScope.launch {
                                val user = viewModel.signup(email, pseudo, password)
                                if (user != null && user.email != null && user.email != ""){
                                    viewModel.createUser(pseudo)
                                    navHostController.navigate(Route.RULES)
                                } else {
                                    Toast.makeText(mContext, "Email ou Mot de passe déjà existant", Toast.LENGTH_SHORT).show()
                                    showError = true
                                }
                            }
                        }
                    },
                    enabled = emailState.value.isNotEmpty() && pseudoState.value.isNotEmpty() && passwordState.value.isNotEmpty(),
                    modifier = Modifier.padding(top = 8.dp),
                    ) {
                    Text(
                        text = "S'inscrire",
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
                Button(
                    onClick = { navHostController.navigate(Route.SIGN_IN) },
                ) {
                    Text(
                        text = "J'ai déja un compte",
                        style = MaterialTheme.typography.titleMedium
                    )

                }
            }
        }
    )
}

fun checkEmailValidity(email: String): Boolean{
    return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
}