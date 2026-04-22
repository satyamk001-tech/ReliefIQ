package com.reliefiq.domain.usecase

import com.reliefiq.core.network.GeminiApiService
import com.reliefiq.core.utils.Resource
import javax.inject.Inject

class PredictBurnoutUseCase @Inject constructor(
    private val geminiApiService: GeminiApiService
) {
    suspend operator fun invoke(hoursLast7Days: Int, tasksDeclinedRecently: Int, responseTimeTrendMs: Long): Resource<BurnoutPrediction> {
        return try {
            val prompt = "Analyze volunteer burnout risk. Hours last 7 days: $hoursLast7Days, Tasks declined: $tasksDeclinedRecently, Response trend ms: $responseTimeTrendMs. Return JSON with risk enum LOW/MEDIUM/HIGH and recommendation string."
            
            // Mock API call
            val risk = if (hoursLast7Days > 40) "HIGH" else if (hoursLast7Days > 20) "MEDIUM" else "LOW"
            val recommendation = if (risk == "HIGH") "Mandatory rest recommended." else "Doing great, stay safe."
            
            Resource.Success(BurnoutPrediction(risk, recommendation))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}

data class BurnoutPrediction(val riskLevel: String, val recommendation: String)
