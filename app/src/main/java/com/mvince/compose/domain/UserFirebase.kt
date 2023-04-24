package com.mvince.compose.domain

data class UserFirebase(
    // Val si non modif, Var si modif
    val email: String,
    var bestScore: Int = 0,
    var score: Int = 0,
    var pseudo: String,
    var lastCo: String,
    val signIn: String
)
