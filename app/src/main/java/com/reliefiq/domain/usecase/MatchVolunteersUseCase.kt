package com.reliefiq.domain.usecase

import com.reliefiq.core.network.GeminiApiService
import com.reliefiq.core.network.GeminiTextRequest
import com.reliefiq.core.utils.Resource
import com.reliefiq.data.model.MatchResult
import com.reliefiq.data.model.Task
import com.reliefiq.data.model.Volunteer
import javax.inject.Inject

class MatchVolunteersUseCase @Inject constructor(
    private val geminiApiService: GeminiApiService
) {
    suspend operator fun invoke(task: Task, availableVolunteers: List<Volunteer>): Resource<List<MatchResult>> {
        // Prepare prompt
        val prompt = buildString {
            append("Task Requirements: Urgency=${task.urgencyLevel}, Skills=${task.requiredSkills}, Hours=${task.estimatedHours}\n")
            append("Available Volunteers:\n")
            availableVolunteers.forEach { v ->
                append("ID: ${v.uid}, Skills: ${v.skills}, Reliability: ${v.reliabilityScore}, Tasks Done: ${v.completedTasks}\n")
            }
            append("Return a JSON array of matched volunteers ranked by fit. Include 'volunteerId', 'fitScore' (0-100), and 'reason'.")
        }

        return try {
            // NOTE: Replace with actual Gemini request format implementation
            // val request = GeminiTextRequest(...)
            // val response = geminiApiService.generateContent("YOUR_API_KEY", request)
            
            // Mock response parsing
            val mockResults = availableVolunteers.take(3).map {
                MatchResult(it.uid, 95f, "High skill overlap and proximity")
            }
            Resource.Success(mockResults)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}
