package com.mvince.compose.ui.Score

import android.annotation.SuppressLint
import android.icu.text.CaseMap.Title
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseUser
import com.mvince.compose.domain.UserFirebase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.forEach

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreTableauScreen(){
    val viewModel = hiltViewModel<ScoreTableauViewModel>()

    val users = viewModel.users.collectAsState().value
    Scaffold() {

        Column {
            users.forEach {
                val user = it as UserFirebase
                Card() {
                    Text(text = user.pseudo)
                }

            }
        }

    }
}