package com.mvince.compose.ui.users

import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mvince.compose.repository.UsersRepository
import com.mvince.compose.ui.MainActivity
import com.mvince.compose.ui.welcomeScreen.WelcomeScreen
import com.mvince.compose.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(UsersUiState())
    val uiState: StateFlow<UsersUiState>
        get() = _uiState

    fun deleteUser(){
        val user = Firebase.auth.currentUser!!

        user.delete().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "User account deleted.")
            }
        }
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
       /* Intent intent = new Intent(MainActivity.this, WelcomeScreen(navHostController = NavHostController()).class);
        statActivity(intent);
        finish()*/
    }


}