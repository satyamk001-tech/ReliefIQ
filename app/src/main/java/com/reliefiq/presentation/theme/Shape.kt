package com.reliefiq.presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp), // 16dp cards
    large = RoundedCornerShape(24.dp),  // 24dp bottom sheets
    extraLarge = RoundedCornerShape(50.dp) // 50dp buttons
)
