package com.reliefiq.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.reliefiq.core.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val functions: FirebaseFunctions
) {
    suspend fun getCurrentUserId(): String? = auth.currentUser?.uid

    suspend fun setCustomRole(uid: String, role: String): Resource<Boolean> {
        return try {
            val data = hashMapOf(
                "uid" to uid,
                "role" to role
            )
            functions.getHttpsCallable("setUserRole").call(data).await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun logout() {
        auth.signOut()
    }
}
