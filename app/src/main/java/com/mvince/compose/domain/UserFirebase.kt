package com.mvince.compose.domain

import java.util.Date

data class UserFirebase(
    // Val si non modif, Var si modif
    var email: String = "",
    var lastPlayed: String = "",
    var bestScore: Int = 0,
    var score: Int = 0,
    var pseudo: String = "",
    var lastCo: String = "",
    val signIn: String = "",
    val avatar: Int = 2131099701
)