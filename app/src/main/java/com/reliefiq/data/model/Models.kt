package com.reliefiq.data.model

import com.google.firebase.firestore.GeoPoint
import java.util.Date

data class Volunteer(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val photoUrl: String = "",
    val skills: List<String> = emptyList(),
    val languages: List<String> = emptyList(),
    val location: GeoPoint? = null,
    val isAvailable: Boolean = false,
    val reliabilityScore: Float = 100f,
    val completedTasks: Int = 0,
    val totalHours: Int = 0,
    val hoursThisWeek: Int = 0,
    val xp: Int = 0,
    val currentBadge: String = Badge.BRONZE_STARTER.name,
    val emergencyContact: String = "",
    val bloodGroup: String = "",
    val fcmToken: String = ""
)

enum class Badge(val title: String, val requiredXp: Int) {
    BRONZE_STARTER("Bronze Starter", 0),
    SILVER_HELPER("Silver Helper", 500),
    GOLD_HERO("Gold Hero", 2000),
    PLATINUM_GUARDIAN("Platinum Guardian", 5000),
    DIAMOND_LEGEND("Diamond Legend", 10000)
}

data class Task(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val urgencyLevel: UrgencyLevel = UrgencyLevel.LOW,
    val status: TaskStatus = TaskStatus.OPEN,
    val requiredSkills: List<String> = emptyList(),
    val location: GeoPoint? = null,
    val volunteersNeeded: Int = 1,
    val volunteersAccepted: Int = 0,
    val estimatedHours: Float = 0f,
    val deadline: Date? = null,
    val damagePhotoUrls: List<String> = emptyList(),
    val weatherContext: String = "",
    val createdBy: String = "",
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

enum class UrgencyLevel { LOW, MEDIUM, HIGH, CRITICAL }
enum class TaskStatus { OPEN, ACCEPTED, IN_PROGRESS, COMPLETED }

data class SOSAlert(
    val id: String = "",
    val volunteerId: String = "",
    val volunteerName: String = "",
    val location: GeoPoint? = null,
    val timestamp: Date = Date(),
    val resolved: Boolean = false,
    val nearestHospital: String = "",
    val respondingVolunteers: List<String> = emptyList()
)

data class ImpactReport(
    val id: String = "",
    val eventId: String = "",
    val volunteerCount: Int = 0,
    val totalHours: Float = 0f,
    val tasksCompleted: Int = 0,
    val areas: List<String> = emptyList(),
    val estimatedLivesImpacted: Int = 0,
    val aiNarrative: String = "",
    val chartData: Map<String, Float> = emptyMap(),
    val pdfUrl: String = "",
    val createdAt: Date = Date()
)

data class MatchResult(
    val volunteerId: String,
    val fitScore: Float,
    val reason: String
)

data class LeaderboardEntry(
    val uid: String = "",
    val period: String = "ALL", // WEEK, MONTH, ALL
    val xp: Int = 0,
    val tasksCompleted: Int = 0,
    val totalHours: Float = 0f,
    val badge: String = Badge.BRONZE_STARTER.name,
    val rank: Int = 0
)

data class WeatherAlert(
    val title: String,
    val description: String,
    val severity: String
)
