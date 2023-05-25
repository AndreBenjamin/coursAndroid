package com.mvince.compose.ui.game

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mvince.compose.domain.Question
import com.mvince.compose.domain.QuestionFirebase
import com.mvince.compose.domain.QuestionResponse
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
    // Initialization of the questions of the day
    private val _questions = MutableStateFlow<List<Question>?>(null)
    val questions: StateFlow<List<Question>?>
        get() = _questions

    private val _currentQuestion = MutableStateFlow<Question?>(null)
    val currentQuestion: StateFlow<Question?>
        get() = _currentQuestion

    private val _response = MutableStateFlow<MutableList<QuestionResponse>?>(null)
    val response: StateFlow<MutableList<QuestionResponse>?>
        get() = _response

    private val _gameScore = MutableStateFlow<Int>(0)
    val gameScore: StateFlow<Int>
        get() = _gameScore

    private val _numQuestion = MutableStateFlow<Int>(0)
    val numQuestion: StateFlow<Int>
        get() = _numQuestion

    private val _gameState = MutableStateFlow<Boolean?>(null)
    val gameState: StateFlow<Boolean?>
        get() = _gameState
    init {
        viewModelScope.launch(Dispatchers.IO) {
            val firebaseQuestion = questionFirebaseRepository.getFireStoreQuestionOfTheDay().collect{
                if(it?.questionList == null){
                    val question = questionsRepository.getQuestionsOfTheDay()
                    _questions.value = question
                    gameInit(question)
                }
                else{
                    val question =  it.questionList
                    _questions.value = it.questionList
                    gameInit(question)
                }
            }
        }

    }

    fun gameInit(questionList: List<Question>){
        _gameState.value = true
        val currentQuestion = questionList[0]
        _currentQuestion.value = currentQuestion

        _response.value = responseCreation(currentQuestion.incorrectAnswer,currentQuestion.correctAnswer);
        _gameScore.value = 0;
    }
    fun responseCreation(wrongAnswer: List<String>, goodAnswer: String):  MutableList<QuestionResponse>{
        val listOfResponse : MutableList<QuestionResponse> = mutableListOf(QuestionResponse(goodAnswer, true))
        wrongAnswer.forEach {
            val result = QuestionResponse(it, false)
            listOfResponse.add(result)
        }
        listOfResponse.shuffle()
        return listOfResponse
    }

    fun validateAnswer(theResponse: QuestionResponse){
        if (theResponse.good){
            _gameScore.update { _gameScore.value + 10 }
        }
        val index =  _numQuestion.value
        val size = questions.value?.size!!
        if(index+1 < size){
            val questionList = questions.value?.get(index +1)
            if (questionList != null) {
                _currentQuestion.update { questionList }
                _response.update {
                    responseCreation(questionList.incorrectAnswer, questionList.correctAnswer)
                }
                _numQuestion.value = index +1
            }
        }else{
            _gameState.update { false }
        }
    }
}