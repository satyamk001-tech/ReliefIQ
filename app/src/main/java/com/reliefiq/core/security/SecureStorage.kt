package com.reliefiq.core.security

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecureStorage @Inject constructor(
    private val encryptedPrefs: SharedPreferences
) {
    fun saveAuthToken(token: String) {
        encryptedPrefs.edit().putString("auth_token", token).apply()
    }

    fun getAuthToken(): String? {
        return encryptedPrefs.getString("auth_token", null)
    }

    fun saveUserRole(role: String) {
        encryptedPrefs.edit().putString("user_role", role).apply()
    }

    fun getUserRole(): String? {
        return encryptedPrefs.getString("user_role", null)
    }

    fun setBiometricEnabled(enabled: Boolean) {
        encryptedPrefs.edit().putBoolean("biometric_enabled", enabled).apply()
    }

    fun isBiometricEnabled(): Boolean {
        return encryptedPrefs.getBoolean("biometric_enabled", false)
    }

    fun updateLastActiveTime(timestamp: Long) {
        encryptedPrefs.edit().putLong("last_active_time", timestamp).apply()
    }

    fun getLastActiveTime(): Long {
        return encryptedPrefs.getLong("last_active_time", 0L)
    }

    fun clearAll() {
        encryptedPrefs.edit().clear().apply()
    }
}
