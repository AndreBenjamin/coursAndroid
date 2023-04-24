package com.mvince.compose.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvince.compose.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: AuthRepository
) : ViewModel() {

    // MutableStateFlow can change its value
    private val _signupFlow = MutableStateFlow<FirebaseUser?>(null)
    // StateFlow can't change its value
    val signupFlow: StateFlow<FirebaseUser?> = _signupFlow

    fun signup(email: String, password: String) {
        viewModelScope.launch {
            _signupFlow.value = repository.signup(email, password)
        }
    }
}