package com.reliefiq.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.reliefiq.core.utils.Resource
import com.reliefiq.data.model.Volunteer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VolunteerRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val volunteerCollection = firestore.collection("volunteers")

    fun getVolunteerProfile(uid: String): Flow<Resource<Volunteer>> = callbackFlow {
        trySend(Resource.Loading)
        val listener = volunteerCollection.document(uid).addSnapshotListener { snapshot, error ->
            if (error != null) {
                trySend(Resource.Error(error))
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                val profile = snapshot.toObject(Volunteer::class.java)
                if (profile != null) trySend(Resource.Success(profile))
            } else {
                trySend(Resource.Error(Exception("Profile not found")))
            }
        }
        awaitClose { listener.remove() }
    }

    suspend fun updateProfile(volunteer: Volunteer): Resource<Boolean> {
        return try {
            volunteerCollection.document(volunteer.uid).set(volunteer).await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}
