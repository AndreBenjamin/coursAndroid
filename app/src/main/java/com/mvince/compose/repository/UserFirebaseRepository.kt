package com.mvince.compose.repository

import android.provider.ContactsContract.CommonDataKinds.Email
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import com.mvince.compose.domain.User
import com.mvince.compose.domain.UserFirebase
import kotlinx.coroutines.flow.map

class UserFirebaseRepository @Inject constructor(private val firestore: FirebaseFirestore) {

    fun insertUser(id: String, user: UserFirebase): Boolean{
        return firestore.collection(_collection).document(id).set(user).isSuccessful
    }

    fun getAll(): Flow<List<UserFirebase>> {
        val test = firestore.collection(_collection).snapshots()
        return test.map { it.toObjects<UserFirebase>() }
    }

    fun getByEmail(email: String): Flow<List<UserFirebase>> {
        return firestore.collection(_collection).whereEqualTo("email", email).limit(1).snapshots().map { it.toObjects<UserFirebase>()}
    }

    fun getTop10(): Flow<List<UserFirebase>>{
        return firestore.collection(_collection).orderBy("score", Query.Direction.DESCENDING).limit(10).snapshots().map { it.toObjects<UserFirebase>() }
    }

    companion object{
        private const val _collection: String = "USER"
    }
}