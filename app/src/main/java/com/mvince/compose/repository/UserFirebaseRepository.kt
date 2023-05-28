package com.mvince.compose.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import com.mvince.compose.domain.UserFirebase
import kotlinx.coroutines.flow.map

class UserFirebaseRepository @Inject constructor(private val firestore: FirebaseFirestore) {

    fun insertUser(id: String, user: UserFirebase): Boolean{
        return firestore.collection(_collection).document(id).set(user).isSuccessful
    }

    fun getAll(): Flow<List<UserFirebase>> {
        return firestore.collection(_collection).snapshots().map { it.toObjects<UserFirebase>() }
    }

    fun getByEmail(email: String?): Flow<List<UserFirebase>> {
        return firestore.collection(_collection).whereEqualTo("email", email).limit(1).snapshots().map { it.toObjects<UserFirebase>()}
    }

    fun getTop10(): Flow<List<UserFirebase>>{
        return firestore.collection(_collection).orderBy("score", Query.Direction.DESCENDING).limit(10).snapshots().map { it.toObjects<UserFirebase>() }
    }

    fun getById(id:String): Flow<UserFirebase?>{
        return firestore.collection(_collection).document(id).snapshots().map{
            it.toObject(UserFirebase::class.java);
        }
    }

    fun updateScore(id: String, score: Int ): Boolean{
        return firestore.collection(_collection).document(id).update("score", score).isSuccessful
    }

    fun updateLastPlayed(id: String, date: String):Boolean{
        return firestore.collection(_collection).document(id).update("lastPlayed", date).isSuccessful
    }

    companion object{
        private const val _collection: String = "USERS"
    }
}