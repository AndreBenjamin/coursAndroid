package com.mvince.compose.ui.signup

import androidx.compose.foundation.gestures.ModifierLocalScrollableContainerProvider.value
import androidx.compose.ui.tooling.data.EmptyGroup.name
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvince.compose.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.google.firebase.auth.FirebaseUser
import com.mvince.compose.domain.UserFirebase
import com.mvince.compose.repository.UserFirebaseRepository
import kotlinx.coroutines.launch
import ro.alexmamo.firebase.auth.OauthRepository
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: OauthRepository,
    private val firebaseRepository: UserFirebaseRepository
) : ViewModel() {

    // MutableStateFlow can change its value
    private val _signupFlow = MutableStateFlow<FirebaseUser?>(null)
    // StateFlow can't change its value
    val signupFlow: StateFlow<FirebaseUser?> = _signupFlow

    private val _isAuthentificated = MutableStateFlow<Boolean>(false)
    val isAuthentificated: StateFlow<Boolean>
            get() = _isAuthentificated

    fun signup(email: String, password: String) {
        viewModelScope.launch {
            val uid = authRepository.signUp(email, password)?.uid
            if (uid != null){
                _isAuthentificated.value = firebaseRepository.insertUser(uid, UserFirebase("me@me.com", 5,0,"melala","2022:12:12","1999:11:12"))
            }
            _signupFlow.value = authRepository.signup(email, password)
        }
    }
}