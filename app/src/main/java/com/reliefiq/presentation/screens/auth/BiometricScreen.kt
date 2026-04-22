package com.reliefiq.presentation.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.reliefiq.core.security.BiometricManager
import com.reliefiq.presentation.components.GradientButton
import com.reliefiq.presentation.components.LottieLoader

@Composable
fun BiometricScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val context = LocalContext.current
    var errorMessage by remember { mutableStateOf<String?>(null) }
    
    val biometricManager = remember { BiometricManager(context) }

    fun triggerBiometric() {
        if (context is FragmentActivity) {
            biometricManager.showBiometricPrompt(
                activity = context,
                onSuccess = { onNavigateToHome() },
                onError = { err -> errorMessage = err }
            )
        } else {
            errorMessage = "Context is not FragmentActivity"
        }
    }

    // Auto-trigger on enter
    LaunchedEffect(Unit) {
        if (biometricManager.canAuthenticate()) {
            triggerBiometric()
        } else {
            errorMessage = "Biometrics not available"
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieLoader(modifier = Modifier.size(150.dp)) // fingerprint lottie
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Verify Identity",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Confirm it is you to continue",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(48.dp))

            GradientButton(
                text = "Authenticate",
                onClick = { triggerBiometric() },
                modifier = Modifier.fillMaxWidth(0.7f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { onNavigateToLogin() }) {
                Text("Use Password Instead", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
