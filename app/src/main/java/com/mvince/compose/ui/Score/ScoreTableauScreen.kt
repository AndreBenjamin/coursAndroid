package com.mvince.compose.ui.Score

import android.annotation.SuppressLint
import android.icu.text.CaseMap.Title
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mvince.compose.domain.User
import com.mvince.compose.domain.UserFirebase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.forEach

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreTableauScreen(){
    val viewModel = hiltViewModel<ScoreTableauViewModel>()

    val users = viewModel.topUsers.collectAsState().value
    val currentUser = viewModel.currentUser.collectAsState().value

    Scaffold() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar (
                title = {
                    Text(text = "Classement")
                }
            )
            
            currentUser.forEach {
                val current = it as UserFirebase
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 24.dp, 16.dp, 32.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 5.dp
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Votre score : ",
                            Modifier
                                .padding(12.dp),
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = current.score.toString(),
                            Modifier
                                .padding(12.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            users.forEach {
                val user = it as UserFirebase
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp, 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = (users.indexOf(user) +1).toString() + "   " + user.pseudo,
                            Modifier
                                .padding(12.dp, 8.dp, 12.dp, 8.dp)
                        )

                        Text(
                            text = user.score.toString(),
                            Modifier
                                .padding(12.dp, 8.dp, 12.dp, 8.dp)
                        )
                    }
                }

            }
        }


    }
}