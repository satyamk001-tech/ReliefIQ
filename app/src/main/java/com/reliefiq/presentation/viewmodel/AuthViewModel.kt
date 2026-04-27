package com.reliefiq.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.reliefiq.core.security.SecureStorage
import com.reliefiq.core.utils.Resource
import com.reliefiq.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val authRepository: AuthRepository,
    private val secureStorage: SecureStorage
) : ViewModel() {

    private val _authState = MutableStateFlow<Resource<Boolean>>(
        if (secureStorage.isDemoMode()) Resource.Success(true) 
        else Resource.Success(auth.currentUser != null)
    )
    val authState: StateFlow<Resource<Boolean>> = _authState.asStateFlow()

    fun login(email: String, pass: String) {
        _authState.value = Resource.Loading
        
        // Demo Account Bypass
        if (email.lowercase() == "volunteer@demo.com" && pass == "password123") {
            secureStorage.setDemoMode(true)
            secureStorage.saveUserRole("volunteer")
            secureStorage.updateLastActiveTime(System.currentTimeMillis())
            _authState.value = Resource.Success(true)
            return
        }
        
        if (email.lowercase() == "admin@demo.com" && pass == "password123") {
            secureStorage.setDemoMode(true)
            secureStorage.saveUserRole("ngo_admin")
            secureStorage.updateLastActiveTime(System.currentTimeMillis())
            _authState.value = Resource.Success(true)
            return
        }

        auth.signInWithEmailAndPassword(email, pass)
            .addOnSuccessListener {
                secureStorage.setDemoMode(false)
                secureStorage.updateLastActiveTime(System.currentTimeMillis())
                _authState.value = Resource.Success(true)
            }
            .addOnFailureListener { e ->
                _authState.value = Resource.Error(e)
            }
    }

    fun register(email: String, pass: String, role: String) {
        _authState.value = Resource.Loading
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid
                if (uid != null) {
                    viewModelScope.launch {
                        authRepository.setCustomRole(uid, role)
                        secureStorage.saveUserRole(role)
                        secureStorage.setDemoMode(false)
                        _authState.value = Resource.Success(true)
                    }
                }
            }
            .addOnFailureListener { e ->
                _authState.value = Resource.Error(e)
            }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            secureStorage.clearAll()
            _authState.value = Resource.Success(false)
        }
    }
}
