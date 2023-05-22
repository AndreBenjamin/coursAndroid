package com.mvince.compose.domain

data class QuestionFirebase(
    var category: String,
    var type: String,
    var difficulty: String,
    var question: String,
    var correctAnswer: String,
    var incorrectAnswer: List<String>
)