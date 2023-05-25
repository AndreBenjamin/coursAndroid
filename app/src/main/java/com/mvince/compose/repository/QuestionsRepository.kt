package com.mvince.compose.repository

import android.util.Log
import androidx.compose.runtime.collectAsState
import com.mvince.compose.domain.Question
import com.mvince.compose.domain.QuestionFirebase
import com.mvince.compose.network.QuestionsOfTheDayApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import java.time.LocalDate
import javax.inject.Inject

class QuestionsRepository @Inject constructor(
    private val api: QuestionsOfTheDayApi,
    private val questionFirebaseRepository: QuestionFirebaseRepository
) {

    suspend fun getQuestionsOfTheDay(): List<Question> {
        val questions = api.getQuestions()
        val questionList = questions.results.map { Question(
            category = it.category,
            type = it.type,
            difficulty = it.difficulty,
            question = it.question,
            correctAnswer = it.correctAnswer,
            incorrectAnswer = it.incorrectAnswers,
        )}

        val insertSuccessful = questionFirebaseRepository.insertQuestion(questionList);

        return questionList
    }


}