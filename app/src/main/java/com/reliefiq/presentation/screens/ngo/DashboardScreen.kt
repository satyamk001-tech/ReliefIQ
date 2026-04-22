package com.reliefiq.presentation.screens.ngo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.reliefiq.presentation.components.GlassCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToPost: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Red Cross Dashboard", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNavigateToPost,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Text("Post Task", fontWeight = FontWeight.Bold)
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                // Summary Cards
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    DashboardStatCard("Active Tasks", "12", Modifier.weight(1f))
                    DashboardStatCard("Deployed", "34", Modifier.weight(1f))
                    DashboardStatCard("Gaps", "3", Modifier.weight(1f), isAlert = true)
                }
            }
            
            item {
                Text("Real-time Activity", style = MaterialTheme.typography.titleLarge, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        ActivityItem("Rahul accepted Medical Supply task", "2 mins ago")
                        ActivityItem("🚨 SOS triggered by Priya", "8 mins ago", isAlert = true)
                        ActivityItem("Task 14 marked complete", "15 mins ago")
                    }
                }
            }

            item {
                Text("Task Kanban Board", style = MaterialTheme.typography.titleLarge, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    item { KanbanColumn("Open", 3) }
                    item { KanbanColumn("Accepted", 5) }
                    item { KanbanColumn("In Progress", 4) }
                }
            }
            
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Composable
fun DashboardStatCard(label: String, value: String, modifier: Modifier = Modifier, isAlert: Boolean = false) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isAlert) Color(0xFFC0392B).copy(alpha=0.2f) else MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, style = MaterialTheme.typography.headlineMedium, color = if (isAlert) Color(0xFFC0392B) else Color.White, fontWeight = FontWeight.Bold)
            Text(label, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun ActivityItem(text: String, time: String, isAlert: Boolean = false) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text, color = if (isAlert) Color(0xFFE74C3C) else Color.White, modifier = Modifier.weight(1f))
        Text(time, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun KanbanColumn(title: String, count: Int) {
    Column(
        modifier = Modifier
            .width(280.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface.copy(alpha=0.5f))
            .padding(16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(title, color = Color.White, fontWeight = FontWeight.Bold)
            Surface(color = MaterialTheme.colorScheme.primary.copy(alpha=0.2f), shape = RoundedCornerShape(12.dp)) {
                Text(count.toString(), color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Mock Kanban Card
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Column {
                Text("Sample Task Title", color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Required: Driving", color = Color.Gray, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}
