package com.mvince.compose.ui.Score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mvince.compose.repository.UserFirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.mvince.compose.domain.UserFirebase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class ScoreTableauViewModel @Inject constructor(
    userFirebaseRepository: UserFirebaseRepository
): ViewModel(){
    //mutable

    val topUsers = userFirebaseRepository.getTop7().stateIn(viewModelScope, SharingStarted.Lazily, emptyList<List<UserFirebase>>())

    val currentUser = userFirebaseRepository.getByEmail(Firebase.auth.currentUser?.email).stateIn(viewModelScope, SharingStarted.Lazily, emptyList<List<UserFirebase>>())
}