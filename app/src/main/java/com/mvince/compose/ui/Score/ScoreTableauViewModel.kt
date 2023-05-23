package com.mvince.compose.ui.Score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvince.compose.repository.UserFirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.mvince.compose.domain.UserFirebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class ScoreTableauViewModel @Inject constructor(
    private val userFirebaseRepository: UserFirebaseRepository
): ViewModel(){
    //mutable

    val users = userFirebaseRepository.getTop10().stateIn(viewModelScope, SharingStarted.Lazily, emptyList<List<UserFirebase>>())


}