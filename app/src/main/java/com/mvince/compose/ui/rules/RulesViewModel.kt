package com.mvince.compose.ui.rules

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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class RulesViewModel @Inject constructor(
    userFirebaseRepository: UserFirebaseRepository,
    private val firebaseRepository: UserFirebaseRepository,

    ) : ViewModel() {

    val currentUser = userFirebaseRepository.getByEmail(Firebase.auth.currentUser?.email).stateIn(viewModelScope, SharingStarted.Lazily, emptyList<List<UserFirebase>>())

    @RequiresApi(Build.VERSION_CODES.O)
    fun modifyUser(email: String, lastPlayed: String, bestScore: Int, score: Int, pseudo: String, lastCo: String, signUp: String, avatar: Int) {
        val user = Firebase.auth.currentUser

        if (user != null){
            if (user.uid != null && user.uid != ""){
                firebaseRepository.insertUser(user.uid, UserFirebase(user.email.toString(), lastPlayed, bestScore,score, pseudo, lastCo, signUp, avatar))
            }
        }
    }
}