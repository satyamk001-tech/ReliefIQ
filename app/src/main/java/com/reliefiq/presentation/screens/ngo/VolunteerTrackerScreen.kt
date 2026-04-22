package com.reliefiq.presentation.screens.ngo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.reliefiq.presentation.components.GlassCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VolunteerTrackerScreen() {
    val mumbai = LatLng(19.0760, 72.8777)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(mumbai, 12f)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Map top half
        Box(modifier = Modifier.weight(0.55f).fillMaxWidth()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(zoomControlsEnabled = false)
            )
            // Filters Over Map
            Row(
                modifier = Modifier.padding(16.dp).align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(selected = true, onClick = {}, label = { Text("All") })
                FilterChip(selected = false, onClick = {}, label = { Text("Available") })
                FilterChip(selected = false, onClick = {}, label = { Text("En Route") })
            }
        }

        // List bottom half
        Box(
            modifier = Modifier
                .weight(0.45f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text("Active Volunteers", style = MaterialTheme.typography.titleMedium, color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                }
                
                // Mock Items
                item { TrackerCard("Priya S.", "EN_ROUTE", "2.1 km away", Color(0xFF3498DB)) }
                item { TrackerCard("Amit V.", "ON_SITE", "0.0 km away", Color(0xFF27AE60)) }
                item { TrackerCard("Neha K.", "AVAILABLE", "5.4 km away", Color.Gray) }
            }
        }
    }
}

@Composable
fun TrackerCard(name: String, status: String, distance: String, statusColor: Color) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(MaterialTheme.colorScheme.surface))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(name, color = Color.White, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(statusColor))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(status, color = statusColor, style = MaterialTheme.typography.labelSmall)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(distance, color = Color.Gray, style = MaterialTheme.typography.labelSmall)
                }
            }
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Chat, contentDescription = "Message", tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
