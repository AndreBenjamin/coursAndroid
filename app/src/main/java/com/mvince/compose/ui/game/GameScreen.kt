package com.mvince.compose.ui.game

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import okio.ByteString.Companion.encodeUtf8


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(navHostController: NavHostController) {
    val viewModel = hiltViewModel<GameViewModel>()
    val currentQuestion = viewModel.currentQuestion.collectAsState().value
    val response = viewModel.response.collectAsState().value
    val gameScore = viewModel.gameScore.collectAsState().value
    val gameState = viewModel.gameState.collectAsState().value
    val numQuestion = viewModel.numQuestion.collectAsState().value +1
    Scaffold(
        topBar = {
            TopAppBar(
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
            if(gameState == true){
                Column(
                    modifier = androidx.compose.ui.Modifier.padding(it),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (currentQuestion != null) {
                        Text(text = "Type : "+ currentQuestion.category.encodeUtf8().utf8())
                        Text(text = "Difficulty : "+ currentQuestion.difficulty.encodeUtf8().utf8())
                        Text(text = currentQuestion.question.encodeUtf8().utf8() ?: "Pas de question disponible")
                    }
                    if (response != null) {
                        Text(text = "Question : " + numQuestion.toString() + "/10")
                        response.forEach {
                            Button(onClick = { viewModel.validateAnswer(it)}) {
                                Text(text = it.reponse.encodeUtf8().utf8())
                            }
                        }
                    }
                    if(gameState == false){
                        Column(modifier = androidx.compose.ui.Modifier.padding(it)) {
                            Text(text = "Game Ended")
                            Text(text = "Your score is : " + gameScore.toString())
                        }
                    }
                }
                Column(modifier = androidx.compose.ui.Modifier.padding(it),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End
                ) {
                    if (gameScore!= null){
                        Text(text = "Score : " + gameScore)
                    }
                }

            }
        }
    )
}