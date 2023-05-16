package com.mvince.compose.repository

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mvince.compose.domain.UserFirebase

// Init Variable
import com.mvince.compose.util.Constants.CREATED_AT
import com.mvince.compose.util.Constants.EMAIL
import com.mvince.compose.util.Constants.ERROR_MESSAGE
import com.mvince.compose.util.Constants.NAME
import com.mvince.compose.util.Constants.PHOTO_URL
import com.mvince.compose.util.Constants.USERS_REF
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OauthRepository @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    suspend fun login(email: String, password: String): FirebaseUser? {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun signUp(email: String, password: String): FirebaseUser? {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun logout() {
        firebaseAuth.signOut()
    }

}