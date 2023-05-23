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
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class RulesViewModel @Inject constructor(
    private val authRepository: OauthRepository,
    private val firebaseRepository: UserFirebaseRepository
) : ViewModel() {

    fun getUserProfile(): FirebaseUser? {
        val user = Firebase.auth.currentUser
        user?.let {
            // TODO: Add Real Info To Return
            val name = it.displayName
            val email = it.email

            // Check if user's email is verified
            val emailVerified = it.isEmailVerified

            // Get Firebase User Id
            val uid = it.uid
        }
        return user
    }
}