package com.reliefiq.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.reliefiq.core.utils.Resource
import com.reliefiq.data.model.Task
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val tasksCollection = firestore.collection("tasks")

    fun getActiveTasks(): Flow<Resource<List<Task>>> = callbackFlow {
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

    suspend fun postTask(task: Task): Resource<String> {
        return try {
            val docRef = tasksCollection.document()
            val newTask = task.copy(id = docRef.id)
            docRef.set(newTask).await()
            Resource.Success(docRef.id)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}
