package com.mvince.compose.ui.signIn

import android.os.Build
import android.text.TextUtils
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.mvince.compose.domain.UserFirebase
import com.mvince.compose.repository.OauthRepository
import com.mvince.compose.repository.UserFirebaseRepository
import hilt_aggregated_deps._com_mvince_compose_ui_game_GameViewModel_HiltModules_KeyModule
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: OauthRepository,
    private val firebaseRepository: UserFirebaseRepository,
    userFirebaseRepository: UserFirebaseRepository
) : ViewModel() {

    // MutableStateFlow can change its value
    private val _signupFlow = MutableStateFlow<UserFirebase?>(null)
    // StateFlow can't change its value
    val signupFlow: StateFlow<UserFirebase?> = _signupFlow

    private val _isSigned = MutableStateFlow<Boolean>(false)
    val isSigned: StateFlow<Boolean>
        get() = _isSigned

    private val _isAuthentificated = MutableStateFlow<Boolean>(false)
    val isAuthentificated: StateFlow<Boolean>
            get() = _isAuthentificated

    val currentUser = userFirebaseRepository.getByEmail(Firebase.auth.currentUser?.email).stateIn(viewModelScope, SharingStarted.Lazily, emptyList<List<UserFirebase>>())

    @RequiresApi(Build.VERSION_CODES.O)
    fun signIn(email: String, password: String) {
        viewModelScope.launch {

            authRepository.signIn(email, password)
                val user = getUserProfile()

            if (user != null) {
                if (user.uid != null){

/*                    // SET DATE
                    val current = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("DD/MM/YYYY")

                    _isAuthentificated.value = firebaseRepository.insertUser(user.uid, UserFirebase(user.email.toString(), bestScore,score, pseudo,current.format(formatter), signIn))
                */
                }

            }
            _isSigned.value = true //authRepository.signIn(email, password)
            
        }
    }


    fun modifyUser(email: String, lastPlayed: String, bestScore: Int, score: Int, pseudo: String, lastCo: String, signUp: String) {
        val user = Firebase.auth.currentUser

        if (user != null){
            if (user.uid != null && user.uid != ""){

                firebaseRepository.insertUser(user.uid, UserFirebase(user.email.toString(), lastPlayed, bestScore,score, pseudo, lastCo, signUp)) // TODO Ben Modifier LastCo
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun resetPassword(email: String) {
        viewModelScope.launch {

            authRepository.sendPasswordResetEmail(email)
        }
    }

    fun getUserProfile(): FirebaseUser? {
        val user = Firebase.auth.currentUser

        return user
    }
}