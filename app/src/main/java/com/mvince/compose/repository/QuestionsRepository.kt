package com.mvince.compose.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.mvince.compose.domain.Question
import com.mvince.compose.network.QuestionsOfTheDayApi
import okio.ByteString.Companion.encodeUtf8
import javax.inject.Inject

class QuestionsRepository @Inject constructor(
    private val api: QuestionsOfTheDayApi,
    private val questionFirebaseRepository: QuestionFirebaseRepository
) {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getQuestionsOfTheDay(): List<Question> {
        val questions = api.getQuestions()
        val questionList = questions.results.map { Question(
            category = it.category.encodeUtf8().utf8(),
            type = it.type.encodeUtf8().utf8(),
            difficulty = it.difficulty.encodeUtf8().utf8(),
            question = it.question.encodeUtf8().utf8(),
            correctAnswer = it.correctAnswer.encodeUtf8().utf8(),
            incorrectAnswer = it.incorrectAnswers,
        )}

        val insertSuccessful = questionFirebaseRepository.insertQuestion(questionList);

        return questionList
    }


}