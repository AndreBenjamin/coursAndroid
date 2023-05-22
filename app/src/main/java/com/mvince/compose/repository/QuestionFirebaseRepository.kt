package com.mvince.compose.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import com.mvince.compose.domain.QuestionFirebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QuestionFirebaseRepository @Inject constructor(private val firestore: FirebaseFirestore) {
    fun insertQuestion(id: String, question: QuestionFirebase): Boolean {
        return firestore.collection(_collection).document(id).set(question).isSuccessful
    }

    fun getAll(): Flow<List<QuestionFirebase>> {
        return firestore.collection(_collection).snapshots().map { it.toObjects<QuestionFirebase>() }
    }

    companion object {
        private const val _collection: String = "QUESTIONS"
    }
}