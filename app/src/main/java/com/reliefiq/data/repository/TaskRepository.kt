package com.reliefiq.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.Query
import com.reliefiq.core.security.SecureStorage
import com.reliefiq.core.utils.Resource
import com.reliefiq.data.model.Task
import com.reliefiq.data.model.UrgencyLevel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val secureStorage: SecureStorage
) {
    private val tasksCollection = firestore.collection("tasks")

    fun getActiveTasks(): Flow<Resource<List<Task>>> {
        if (secureStorage.isDemoMode()) {
            return flow {
                emit(Resource.Loading)
                kotlinx.coroutines.delay(1000)
                emit(Resource.Success(getMockTasks()))
            }
        }
        
        return callbackFlow {
            trySend(Resource.Loading)
            val listener = tasksCollection
                .whereEqualTo("status", "OPEN")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        trySend(Resource.Error(error))
                        return@addSnapshotListener
                    }
                    if (snapshot != null) {
                        val tasks = snapshot.toObjects(Task::class.java)
                        trySend(Resource.Success(tasks))
                    }
                }
            awaitClose { listener.remove() }
        }
    }

    suspend fun postTask(task: Task): Resource<String> {
        if (secureStorage.isDemoMode()) return Resource.Success("mock_task_id")
        return try {
            val docRef = tasksCollection.document()
            val newTask = task.copy(id = docRef.id)
            docRef.set(newTask).await()
            Resource.Success(docRef.id)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    private fun getMockTasks(): List<Task> {
        return listOf(
            Task(
                id = "1",
                title = "Medical Supplies Delivery",
                description = "Urgent need for delivery of medical kits to central hospital area.",
                urgencyLevel = UrgencyLevel.CRITICAL,
                requiredSkills = listOf("Driving", "Medical"),
                volunteersNeeded = 3,
                volunteersAccepted = 1,
                createdBy = "Red Cross",
                location = GeoPoint(19.0760, 72.8777)
            ),
            Task(
                id = "2",
                title = "Debris Clearance - Sector 4",
                description = "Clearance of debris from main road to allow emergency vehicles.",
                urgencyLevel = UrgencyLevel.HIGH,
                requiredSkills = listOf("Construction", "Logistics"),
                volunteersNeeded = 10,
                volunteersAccepted = 4,
                createdBy = "Municipal Corp",
                location = GeoPoint(19.0800, 72.8800)
            ),
            Task(
                id = "3",
                title = "Temporary Shelter Setup",
                description = "Setting up tents and food stalls for displaced residents.",
                urgencyLevel = UrgencyLevel.MEDIUM,
                requiredSkills = listOf("Construction", "Cooking"),
                volunteersNeeded = 15,
                volunteersAccepted = 8,
                createdBy = "Relief NGO",
                location = GeoPoint(19.0700, 72.8700)
            )
        )
    }
}
