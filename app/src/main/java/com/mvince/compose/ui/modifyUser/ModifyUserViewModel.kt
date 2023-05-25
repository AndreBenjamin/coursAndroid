package com.mvince.compose.ui.modifyUser

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mvince.compose.domain.UserFirebase
import com.mvince.compose.repository.UserFirebaseRepository
import com.mvince.compose.repository.UsersRepository
import com.mvince.compose.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine


@HiltViewModel
class ModifyUserViewModel @Inject constructor(
    userFirebaseRepository: UserFirebaseRepository
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

}