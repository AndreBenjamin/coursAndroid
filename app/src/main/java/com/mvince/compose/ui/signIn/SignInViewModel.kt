package com.mvince.compose.ui.signIn

import android.annotation.SuppressLint
import android.os.Build
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
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
import com.mvince.compose.ui.Route
import hilt_aggregated_deps._com_mvince_compose_ui_game_GameViewModel_HiltModules_KeyModule
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: OauthRepository,
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

    suspend fun signIn(email: String, password: String): FirebaseUser? {
        return viewModelScope.async {
            authRepository.signIn(email, password)
        }.await()
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