package com.reliefiq.presentation.screens.volunteer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
fun MyContributionsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Contributions") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                // Profile Header with XP Ring
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.size(120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            progress = { 0.75f }, // Example 75% to next badge
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 6.dp
                        )
                        Box(
                            modifier = Modifier.size(100.dp).clip(CircleShape).background(MaterialTheme.colorScheme.surface)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Rahul Sharma", style = MaterialTheme.typography.headlineMedium, color = Color.White, fontWeight = FontWeight.Bold)
                    Text("Gold Hero • 2,450 XP", color = Color(0xFFF39C12), fontWeight = FontWeight.Bold)
                }
            }
            
            item {
                // Stats Grid
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    StatCard("Tasks Done", "14", Modifier.weight(1f))
                    StatCard("Hours Given", "48", Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    StatCard("Lives Impacted", "~120", Modifier.weight(1f))
                    StatCard("Current Streak", "3 🔥", Modifier.weight(1f))
                }
            }
            
            item {
                // Burnout Warning
                GlassCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row {
                        Text("⚠️", style = MaterialTheme.typography.headlineMedium)
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("Burnout Risk: MEDIUM", color = Color(0xFFF39C12), fontWeight = FontWeight.Bold)
                            Text("You've contributed 20 hours this week. Consider taking a rest.", color = Color.White, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
            
            item {
                Text("Past Tasks", style = MaterialTheme.typography.headlineSmall, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                // Mock Timeline
                TimelineItem("Food Distribution at Camp 2", "2 days ago", "4 hrs")
                TimelineItem("Medical Supply Transport", "5 days ago", "6 hrs")
            }
        }
    }
}

@Composable
fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    GlassCard(modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Text(value, style = MaterialTheme.typography.headlineMedium, color = Color.White, fontWeight = FontWeight.Bold)
            Text(label, color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun TimelineItem(title: String, date: String, duration: String) {
    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        Box(modifier = Modifier.padding(top=4.dp).size(12.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary))
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(title, color = Color.White, fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(date, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                Text(duration, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
