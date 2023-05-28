package com.mvince.compose.ui.modifyUser

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mvince.compose.domain.UserFirebase
import com.mvince.compose.repository.UserFirebaseRepository
import com.mvince.compose.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine


@HiltViewModel
class ModifyUserViewModel @Inject constructor(
    private val firebaseRepository: UserFirebaseRepository,
    userFirebaseRepository: UserFirebaseRepository
) : ViewModel() {
    fun modifyUserPswd(password: String) {
        val user = Firebase.auth.currentUser
        val newPassword = password

        user!!.updatePassword(newPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User password updated.")
                }
            }
    }
    val currentUser = userFirebaseRepository.getByEmail(Firebase.auth.currentUser?.email).stateIn(viewModelScope, SharingStarted.Lazily, emptyList<List<UserFirebase>>())

    fun modifyUser(
        email: String,
        lastPlayed: String,
        bestScore: Int,
        score: Int,
        pseudo: String,
        lastCo: String,
        signUp: String,
        imageResId: Int
    ) {
        val user = Firebase.auth.currentUser

        if (user != null){
            if (user.uid != null && user.uid != ""){
                firebaseRepository.insertUser(user.uid, UserFirebase(user.email.toString(), lastPlayed, bestScore,score, pseudo, lastCo, signUp, imageResId))
            }
        }
    }
}