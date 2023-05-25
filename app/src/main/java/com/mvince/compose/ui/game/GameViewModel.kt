package com.mvince.compose.ui.game

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mvince.compose.domain.Question
import com.mvince.compose.domain.QuestionFirebase
import com.mvince.compose.repository.QuestionsRepository
import com.mvince.compose.network.model.Result
import com.mvince.compose.repository.QuestionFirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Optional
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val questionsRepository: QuestionsRepository,
    private val questionFirebaseRepository: QuestionFirebaseRepository
): ViewModel() {
    // Initialization of the current question
    //val currentQuestion = questionFirebaseRepository.getAll().stateIn(viewModelScope, SharingStarted.Lazily, emptyList<List<Question>>())

    // Initialization of the questions of the day
    private val _questions = MutableStateFlow<List<Question>?>(null)
    val questions: StateFlow<List<Question>?>
        get() = _questions

    private val _currentQuestion = MutableStateFlow<Question?>(null)
    val currentQuestion: StateFlow<Question?>
        get() = _currentQuestion

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val questionsList = questionsRepository.getQuestionsOfTheDay();
            val currentQuestionOfTheday = questionsList[0];
            _questions.update{ questionsList }
            _currentQuestion.update { currentQuestionOfTheday }

        }

    }
}