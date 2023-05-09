package com.mvince.compose.network

import com.mvince.compose.network.model.QuestionsOfTheDayModelApi
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionsOfTheDayApi {
    @GET("api.php")
    suspend fun getQuestions(@Query("amount") amount: Int = 10): QuestionsOfTheDayModelApi
}