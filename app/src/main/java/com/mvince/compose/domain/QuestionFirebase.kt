package com.mvince.compose.domain

data class QuestionFirebase(
    val questionList : List<Question>
){
    constructor() : this(emptyList())
}