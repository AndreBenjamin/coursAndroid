package com.mvince.compose.ui.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mvince.compose.domain.Question
import kotlinx.coroutines.flow.forEach


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen() {
    val viewModel = hiltViewModel<GameViewModel>()
    val currentQuestion = viewModel.currentQuestion.collectAsState().value
    val questions = viewModel.questions.collectAsState().value
    Scaffold() {
        //Text(text = "Working")
        if (currentQuestion != null) {
            Text(text = currentQuestion.question ?: "Pas de question disponible")
        }
        Column(modifier = androidx.compose.ui.Modifier.padding(it)) {

            /*if (questions != null) {
                questions.forEach() {
                    val question = it as Question
                    Text(text = question.question ?: "Pas de question disponible")
                }
            }*/
            //Text(text = currentQuestion?.question ?: "Pas de question disponible")
            /*Button(onClick = { viewModel.validateAnswers(currentQuestion, chosenAnswers)}) {
                
            }*/
        }
    }
}