package com.mvince.compose.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvince.compose.repository.QuestionsRepository
import com.mvince.compose.network.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(private val questionsRepository: QuestionsRepository): ViewModel() {

    private val _currentQuestion = MutableStateFlow<Result?>(null)
    val currentQuestion: StateFlow<Result?>
        get() = _currentQuestion

    private val _questions = flow {
        val questions = questionsRepository.getQuestionsOfTheDay()
        emit(questions)
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val questions: StateFlow<List<Result>>
        get() = _questions

    fun validateAnswers(/*test: Result*/ index: Int){
        //test.question
        //listOf<String>(test.correctAnswer, test.incorrectAnswers.flatMap {  })

        _currentQuestion.update {
            _questions.value.get(index)
        }
    }
}