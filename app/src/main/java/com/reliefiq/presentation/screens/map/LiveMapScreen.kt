package com.reliefiq.presentation.screens.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.reliefiq.core.utils.Resource
import com.reliefiq.data.model.Task
import com.reliefiq.data.model.UrgencyLevel
import com.reliefiq.presentation.viewmodel.MapViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveMapScreen(
    viewModel: MapViewModel = hiltViewModel()
) {
    val tasksState by viewModel.tasks.collectAsState()
    val mumbai = LatLng(19.0760, 72.8777)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(mumbai, 11f)
    }
    
    // In a real app we'd load the JSON string from res/raw/map_style.json
    val mapProperties = remember {
        MapProperties(
            isMyLocationEnabled = true, // Requires permission check in real impl
            mapType = MapType.NORMAL,
            // mapStyleOptions = MapStyleOptions(mapStyleJson)
        )
    }
    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false, myLocationButtonEnabled = false)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Animate camera to my location */ },
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 80.dp) // Leave space for bottom sheet
            ) {
                Icon(Icons.Filled.MyLocation, "My Location")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = mapProperties,
                uiSettings = uiSettings
            ) {
                if (tasksState is Resource.Success) {
                    val tasks = (tasksState as Resource.Success<List<Task>>).data
                    // Simple markers for tasks. In reality, use clustering via maps-compose-utils
                    tasks.forEach { task ->
                        if (task.location != null) {
                            val pos = LatLng(task.location!!.latitude, task.location!!.longitude)
                            Marker(
                                state = MarkerState(position = pos),
                                title = task.title,
                                snippet = "Urgency: ${task.urgencyLevel}"
                            )
                        }
                    }
                }
            }

            // Top Search Bar & Filters
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.TopCenter)
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Search location...", color = Color.Gray) },
                    leadingIcon = { Icon(Icons.Filled.Search, null, tint = Color.Gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surface.copy(alpha=0.9f)),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(selected = true, onClick = {}, label = { Text("All") })
                    FilterChip(selected = false, onClick = {}, label = { Text("Critical") }, colors = FilterChipDefaults.filterChipColors(selectedContainerColor = Color(0xFFC0392B)))
                    FilterChip(selected = false, onClick = {}, label = { Text("Near Me") })
                }
            }

            // Mock Bottom Sheet Handle (We'd use BottomSheetScaffold in full impl)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.width(40.dp).height(4.dp).clip(RoundedCornerShape(2.dp)).background(Color.Gray))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Tasks in area", style = MaterialTheme.typography.titleMedium, color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Swipe up to view list", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
