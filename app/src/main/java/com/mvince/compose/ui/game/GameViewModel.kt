package com.mvince.compose.ui.game

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mvince.compose.domain.Question
import com.mvince.compose.domain.QuestionResponse
import com.mvince.compose.domain.UserFirebase
import com.mvince.compose.repository.QuestionsRepository
import com.mvince.compose.repository.QuestionFirebaseRepository
import com.mvince.compose.repository.UserFirebaseRepository
import com.mvince.compose.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class GameViewModel @Inject constructor(
    private val questionsRepository: QuestionsRepository,
    private val questionFirebaseRepository: QuestionFirebaseRepository,
    private val userFirebaseRepository: UserFirebaseRepository
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

    val user = Firebase.auth.currentUser

    private val _currentUser = MutableStateFlow<UserFirebase?>(null)
    val currentUser: StateFlow<UserFirebase?>
        get()=_currentUser

    private var _currentUserScore = 0
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
                if(user?.uid != null && user?.uid != "") {
                    userFirebaseRepository.getById(user.uid).collect {
                        if (it != null) {
                            _currentUser.value = it
                            _currentUserScore = it.score
                        }
                    }
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
            val newScore = _currentUserScore + _gameScore.value
            if(_currentUser.value?.lastPlayed != LocalDate.now().toString()){
                user?.let {
                    userFirebaseRepository.updateScore(it.uid, newScore)
                    userFirebaseRepository.updateLastPlayed(it.uid, LocalDate.now().toString())
                }
            }

            _gameState.update { false }
        }
    }
}