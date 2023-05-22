package com.mvince.compose.repository

import com.mvince.compose.domain.Question
import com.mvince.compose.network.QuestionsOfTheDayApi
import javax.inject.Inject

class QuestionsRepository @Inject constructor(
    private val api: QuestionsOfTheDayApi
) {

    suspend fun getQuestionsOfTheDay(): List<Question> {
        val questions = api.getQuestions()
        return questions.results.map { Question(
            category = it.category,
            type = it.type,
            difficulty = it.difficulty,
            question = it.question,
            correctAnswer = it.correctAnswer,
            incorrectAnswer = it.incorrectAnswers,
        )}
    }


}