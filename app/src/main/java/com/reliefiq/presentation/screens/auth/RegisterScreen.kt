package com.reliefiq.presentation.screens.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.reliefiq.core.utils.Resource
import com.reliefiq.presentation.components.GradientButton
import com.reliefiq.presentation.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    onNavigateToProfileSetup: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var role by remember { mutableStateOf("volunteer") } // "volunteer" or "ngo_admin"
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    
    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is Resource.Success && (authState as Resource.Success<Boolean>).data) {
            if (role == "volunteer") onNavigateToProfileSetup()
            else onNavigateToDashboard()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D1A))
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Create Account",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))
            
            // Role Selection
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                RoleCard(
                    title = "Volunteer",
                    isSelected = role == "volunteer",
                    onClick = { role = "volunteer" },
                    modifier = Modifier.weight(1f)
                )
                RoleCard(
                    title = "NGO Admin",
                    isSelected = role == "ngo_admin",
                    onClick = { role = "ngo_admin" },
                    modifier = Modifier.weight(1f)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Simplistic password strength bar
            val strength = minOf(password.length / 10f, 1f)
            val strengthColor = when {
                strength < 0.4f -> Color.Red
                strength < 0.8f -> Color(0xFFF39C12)
                else -> Color(0xFF27AE60)
            }
            LinearProgressIndicator(
                progress = { strength },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(4.dp),
                color = strengthColor,
                trackColor = MaterialTheme.colorScheme.surface
            )

            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            AnimatedVisibility(visible = errorMessage != null || authState is Resource.Error) {
                val msg = errorMessage ?: (authState as? Resource.Error)?.exception?.message ?: ""
                Text(
                    text = msg,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            GradientButton(
                text = "Create Account",
                isLoading = authState is Resource.Loading,
                onClick = {
                    if (password != confirmPassword) {
                        errorMessage = "Passwords do not match"
                    } else if (password.length < 6) {
                        errorMessage = "Password too short"
                    } else {
                        errorMessage = null
                        viewModel.register(email, password, role)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
        
        Text(
            text = "Back to Login",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clickable { onNavigateToLogin() }
                .padding(16.dp)
        )
    }
}

@Composable
fun RoleCard(title: String, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface)
            .border(
                width = 2.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = title, color = if (isSelected) MaterialTheme.colorScheme.primary else Color.White, fontWeight = FontWeight.Bold)
    }
}
