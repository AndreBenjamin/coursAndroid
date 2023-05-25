package com.mvince.compose.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import com.mvince.compose.domain.Question
import com.mvince.compose.domain.QuestionFirebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class QuestionFirebaseRepository @Inject constructor(
    private val firestore: FirebaseFirestore
    ) {
    fun insertQuestion(questions: List<Question>): Boolean {

        return firestore.collection(_collection).document(LocalDate.now().toString()).set(QuestionFirebase(questions)).isSuccessful
    }

    fun getAll(): Flow<List<Question>> {
        return firestore.collection(_collection).snapshots().map { it.toObjects<Question>() }
    }

    /*fun getById(id: String): Flow<List<Question>>{
        return firestore.collection(_collection).get(id)
    }*/

    companion object {
        private const val _collection: String = "QUESTIONS"
    }
}