package com.mvince.compose.ui.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.mvince.compose.domain.UserFirebase
import com.mvince.compose.repository.OauthRepository
import com.mvince.compose.repository.UserFirebaseRepository
import com.mvince.compose.ui.MainActivity
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: OauthRepository,
    private val firebaseRepository: UserFirebaseRepository
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

    fun signup(email: String, password: String) {
        viewModelScope.launch {
            authRepository.signUp(email, password)
                val user = getUserProfile()
            if (user != null) {
                if (user.uid != null){
                    _isAuthentificated.value = firebaseRepository.insertUser(user.uid, UserFirebase("me@me.com", 5,0,"melala","2022:12:12","1999:11:12"))
                }
            }
            _isSigned.value = true //authRepository.signUp(email, password)
        }
    }

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