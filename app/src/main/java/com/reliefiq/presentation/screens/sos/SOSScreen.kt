package com.reliefiq.presentation.screens.sos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.reliefiq.presentation.components.PulsingSOSButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SOSScreen(
    onCancel: () -> Unit
) {
    var sosTriggered by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFC0392B), Color(0xFF7B0000))
                )
            )
    ) {
        IconButton(
            onClick = onCancel,
            modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)
        ) {
            Icon(Icons.Filled.Close, contentDescription = "Cancel", tint = Color.White)
        }

        if (!sosTriggered) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PulsingSOSButton(
                    modifier = Modifier.size(250.dp),
                    onTrigger = { sosTriggered = true }
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    "HOLD 3 SECONDS TO SEND SOS",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            
            // Bottom Info
            Column(
                modifier = Modifier.align(Alignment.BottomCenter).padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Emergency Contact: +1 234 567 890", color = Color.White.copy(alpha=0.8f))
                Spacer(modifier = Modifier.height(8.dp))
                Text("Nearest Hospital: City Med (2.4 km)", color = Color.White.copy(alpha=0.8f))
            }
        } else {
            // Success State
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "HELP IS ON THE WAY",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Black
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Alert sent to emergency contacts and nearby volunteers.",
                    color = Color.White.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(32.dp))
                CircularProgressIndicator(color = Color.White)
                Spacer(modifier = Modifier.height(16.dp))
                Text("2 volunteers responding (ETA 4 mins)", color = Color.White)
            }
        }
    }
}
