package com.mvince.compose.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvince.compose.domain.Question
import com.mvince.compose.domain.QuestionFirebase
import com.mvince.compose.repository.QuestionsRepository
import com.mvince.compose.network.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val questionsRepository: QuestionsRepository
): ViewModel() {
    // Initialization of the current question
    private val _currentQuestion = MutableStateFlow<QuestionFirebase?>(null)

    val currentQuestion: StateFlow<QuestionFirebase?> = _currentQuestion

    // Initialization of the questions of the day
    private val _questions = flow {
        val questions = questionsRepository.getQuestionsOfTheDay()
        emit(questions)
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val questions: StateFlow<List<Question>> = _questions

    // Validate the answer selected by the user
    fun validateAnswers(/*test: Result*/ index: Int){
        //test.question
        //listOf<String>(test.correctAnswer, test.incorrectAnswers.flatMap {  })

        /*_currentQuestion.update {
            _questions.value.get(index)
        }*/
    }
}