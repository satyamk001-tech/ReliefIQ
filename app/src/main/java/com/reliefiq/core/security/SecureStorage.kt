package com.reliefiq.core.security

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecureStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var _encryptedPrefs: SharedPreferences? = null

    private fun getPrefs(): SharedPreferences {
        return _encryptedPrefs ?: synchronized(this) {
            _encryptedPrefs ?: createPrefs().also { _encryptedPrefs = it }
        }
    }

    private fun createPrefs(): SharedPreferences {
        // We use runBlocking only if called from a place that doesn't support suspend, 
        // but ideally this should be initialized on a background thread.
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            "relief_iq_secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveAuthToken(token: String) {
        getPrefs().edit().putString("auth_token", token).apply()
    }

    fun getAuthToken(): String? {
        return getPrefs().getString("auth_token", null)
    }

    fun saveUserRole(role: String) {
        getPrefs().edit().putString("user_role", role).apply()
    }

    fun getUserRole(): String? {
        return getPrefs().getString("user_role", null)
    }

    fun setBiometricEnabled(enabled: Boolean) {
        getPrefs().edit().putBoolean("biometric_enabled", enabled).apply()
    }

    fun isBiometricEnabled(): Boolean {
        return getPrefs().getBoolean("biometric_enabled", false)
    }

    fun updateLastActiveTime(timestamp: Long) {
        getPrefs().edit().putLong("last_active_time", timestamp).apply()
    }

    fun getLastActiveTime(): Long {
        return getPrefs().getLong("last_active_time", 0L)
    }

    fun setDemoMode(enabled: Boolean) {
        getPrefs().edit().putBoolean("is_demo_mode", enabled).apply()
    }

    fun isDemoMode(): Boolean {
        return getPrefs().getBoolean("is_demo_mode", false)
    }

    fun clearAll() {
        getPrefs().edit().clear().apply()
    }
}
