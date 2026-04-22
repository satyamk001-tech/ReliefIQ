package com.reliefiq.domain.usecase

import com.google.firebase.firestore.FirebaseFirestore
import com.reliefiq.core.network.TwilioService
import com.reliefiq.core.utils.Resource
import com.reliefiq.data.model.SOSAlert
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TriggerSOSUseCase @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val twilioService: TwilioService
) {
    suspend operator fun invoke(alert: SOSAlert, emergencyContactPhone: String): Resource<Boolean> {
        return try {
            // 1. Save alert to Firestore
            val docRef = firestore.collection("sos_alerts").document()
            val newAlert = alert.copy(id = docRef.id)
            docRef.set(newAlert).await()

            // 2. Send SMS via Twilio
            // Note: In real app, securely inject or fetch account SID and token
            val messageBody = "🚨 SOS ALERT from ${alert.volunteerName}! Location: https://maps.google.com/?q=${alert.location?.latitude},${alert.location?.longitude}"
            
            // twilioService.sendSms(
            //     accountSid = "...", 
            //     authHeader = "Basic ...", 
            //     from = "...", 
            //     to = emergencyContactPhone, 
            //     body = messageBody
            // )

            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}
