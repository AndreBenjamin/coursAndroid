package com.mvince.compose.network

import com.mvince.compose.network.model.QuestionsOfTheDayModelApi
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionsOfTheDayApi {
    //TODO: faut-il plutôt passer en List<QuestionApiModel> ? (pour le retour)
    @GET("https://opentdb.com/api.php")
    suspend fun getQuestions(@Query("amount") amount: Int = 10): QuestionsOfTheDayModelApi
}