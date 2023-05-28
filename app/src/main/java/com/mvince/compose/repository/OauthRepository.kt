package com.mvince.compose.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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

    suspend fun signIn(email: String, password: String): FirebaseUser? {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun sendPasswordResetEmail(email: String): Boolean? {
        return try {
            val result = firebaseAuth.sendPasswordResetEmail(email).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun logout() {
        firebaseAuth.signOut()
    }

}