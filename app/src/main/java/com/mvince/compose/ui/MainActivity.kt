package com.mvince.compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mvince.compose.ui.theme.JetpackComposeBoilerplateTheme
import dagger.hilt.android.AndroidEntryPoint
import com.mvince.compose.domain.UserFirebase

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeBoilerplateTheme {
                ComposeApp()
            }
        }
    }

    fun getUserProfile(): FirebaseUser? {
        val user = Firebase.auth.currentUser
        user?.let {
            // TODO Add Real Info To Return
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