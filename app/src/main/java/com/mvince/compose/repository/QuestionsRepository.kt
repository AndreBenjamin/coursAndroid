package com.mvince.compose.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.mvince.compose.network.QuestionsOfTheDayApi
import javax.inject.Inject
import com.mvince.compose.network.model.Result
// import com.mvince.compose.network.QuestionsOfTheDayApi

class QuestionsRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val api: QuestionsOfTheDayApi
    ) {

    suspend fun getQuestionsOfTheDay(): List<Result> {
        val response = api.getQuestions()
        return response.results
    }

}