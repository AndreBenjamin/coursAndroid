package com.mvince.compose.network.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuestionsOfTheDayModelApi(
    @Json(name = "response_code")
    val responseCode: Int,
    @Json(name = "results")
    val results: List<Result>
)