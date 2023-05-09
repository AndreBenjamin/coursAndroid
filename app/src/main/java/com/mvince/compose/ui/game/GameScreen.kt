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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen() {
    val viewModel = hiltViewModel<GameViewModel>()
    
    val currentQuestion = viewModel.currentQuestion.collectAsState().value
    Scaffold() {
        Column(modifier = androidx.compose.ui.Modifier.padding(it)) {
            Text(text = currentQuestion?.question ?:"")
            Button(onClick = { viewModel.validateAnswers(currentQuestion, choosenAnswers)}) {
                
            }
            
        }
        
    }
    
}