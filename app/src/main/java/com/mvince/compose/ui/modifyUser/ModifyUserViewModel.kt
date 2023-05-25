package com.mvince.compose.ui.modifyUser

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mvince.compose.repository.UsersRepository
import com.mvince.compose.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine


@HiltViewModel
class ModifyUserViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {

    /*  fun modifyUserPswd(password: String): Boolean {
          val user = Firebase.auth.currentUser
          val newPassword = password*/

    /* user!!.updatePassword(newPassword)
         .addOnCompleteListener { task ->
             if (task.isSuccessful) {
                   Log.d(TAG, "User password updated.")
                 // TODO Donia: AFFICHER UNE POPUP
                 true
             } else false
         }
 }*/
    suspend fun modifyUserPswd(password: String): Boolean = suspendCoroutine { continuation ->
        val user = Firebase.auth.currentUser
        val newPassword = password

        user!!.updatePassword(newPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User password updated.")
                    // TODO: Afficher une popup ou effectuer d'autres opÃ©rations
                    continuation.resumeWith(Result.success(true))
                } else {
                    continuation.resumeWith(Result.success(false))
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Failed to update user password: ${exception.message}")
                continuation.resumeWith(Result.success(false))
            }
    }
}