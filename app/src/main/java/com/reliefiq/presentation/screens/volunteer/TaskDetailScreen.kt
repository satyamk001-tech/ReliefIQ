package com.reliefiq.presentation.screens.volunteer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.reliefiq.data.model.Task
import com.reliefiq.data.model.UrgencyLevel
import com.reliefiq.presentation.components.GlassCard
import com.reliefiq.presentation.components.GradientButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskId: String,
    onNavigateBack: () -> Unit,
    onAcceptTask: () -> Unit
) {
    // In real app, fetch task by ID from ViewModel.
    // Mock task for display purposes:
    val task = Task(
        id = taskId,
        title = "Medical Supplies Delivery",
        description = "Need 3 volunteers to deliver urgent medical supplies to the flood relief camp at Sector 4.",
        urgencyLevel = UrgencyLevel.CRITICAL,
        requiredSkills = listOf("Driving", "First Aid"),
        volunteersNeeded = 3,
        volunteersAccepted = 1,
        estimatedHours = 4.5f,
        createdBy = "Red Cross"
    )

    val urgencyColor = when(task.urgencyLevel) {
        UrgencyLevel.CRITICAL -> Color(0xFFC0392B)
        UrgencyLevel.HIGH -> Color(0xFFE74C3C)
        UrgencyLevel.MEDIUM -> Color(0xFFF39C12)
        UrgencyLevel.LOW -> Color(0xFF27AE60)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Placeholder for image carousel
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Text("Damage Photos ViewPager", color = Color.Gray, modifier = Modifier.align(Alignment.Center))
            }
            
            Column(modifier = Modifier.padding(16.dp)) {
                // Header
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(color = urgencyColor.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp)) {
                        Text(
                            text = task.urgencyLevel.name,
                            color = urgencyColor,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "By ${task.createdBy}", color = Color.LightGray)
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Description
                Text(text = "Description", color = Color.Gray, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = task.description, color = Color.White)
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Location Row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.LocationOn, contentDescription = "Location", tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Sector 4 Relief Camp (Tap to view map)", color = MaterialTheme.colorScheme.primary)
                }

                Spacer(modifier = Modifier.height(24.dp))
                
                // AI Match Score
                GlassCard {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(30.dp))
                                .background(MaterialTheme.colorScheme.primary.copy(alpha=0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("95%", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineSmall)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("Why You Match", fontWeight = FontWeight.Bold, color = Color.White)
                            Text("You have the required Driving and First Aid skills, and you are currently 2km away.", color = Color.LightGray, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Progress
                Text("Volunteers Progress", color = Color.Gray, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { task.volunteersAccepted.toFloat() / task.volunteersNeeded },
                    modifier = Modifier.fillMaxWidth().height(8.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                Text("${task.volunteersAccepted} accepted out of ${task.volunteersNeeded}", color = Color.LightGray, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(top=4.dp))

                Spacer(modifier = Modifier.height(48.dp))
                
                // Actions
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                    ) {
                        Text("Decline")
                    }
                    GradientButton(
                        text = "Accept Task",
                        onClick = onAcceptTask,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
