package com.reliefiq.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun PulsingSOSButton(
    modifier: Modifier = Modifier,
    onTrigger: () -> Unit
) {
    var isHolding by remember { mutableStateOf(false) }
    var holdProgress by remember { mutableStateOf(0f) }

    val infiniteTransition = rememberInfiniteTransition(label = "Pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "PulseScale"
    )
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Restart
        ),
        label = "PulseAlpha"
    )

    LaunchedEffect(isHolding) {
        if (isHolding) {
            while (holdProgress < 1f) {
                delay(30)
                holdProgress += 0.01f
            }
            if (holdProgress >= 1f) {
                onTrigger()
                isHolding = false
                holdProgress = 0f
            }
        } else {
            holdProgress = 0f
        }
    }

    val buttonScale by animateFloatAsState(
        targetValue = if (isHolding) 0.9f else 1f,
        label = "SOSScale"
    )

    Box(
        modifier = modifier.size(200.dp),
        contentAlignment = Alignment.Center
    ) {
        // Outer pulsing ring
        Box(
            modifier = Modifier
                .fillMaxSize()
                .scale(pulseScale)
                .background(Color(0xFFE74C3C).copy(alpha = pulseAlpha), CircleShape)
        )

        // Hold progress ring (simulated with solid background for now)
        if (holdProgress > 0) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.3f * holdProgress))
            )
        }

        // Main button
        Box(
            modifier = Modifier
                .size(140.dp)
                .scale(buttonScale)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color(0xFFFF5252), Color(0xFFC0392B))
                    )
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isHolding = true
                            tryAwaitRelease()
                            isHolding = false
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "SOS",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}
