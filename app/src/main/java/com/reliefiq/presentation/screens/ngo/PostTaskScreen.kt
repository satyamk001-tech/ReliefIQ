package com.reliefiq.presentation.screens.ngo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.reliefiq.presentation.components.GradientButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostTaskScreen(
    onNavigateBack: () -> Unit
) {
    var isAiMode by remember { mutableStateOf(false) }
    var aiPrompt by remember { mutableStateOf("") }
    var taskTitle by remember { mutableStateOf("") }
    var urgency by remember { mutableStateOf("MEDIUM") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Post New Task") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Mode Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(50.dp))
                        .background(if (!isAiMode) MaterialTheme.colorScheme.primary else Color.Transparent)
                        .clickable { isAiMode = false }
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Manual", color = if (!isAiMode) Color.White else Color.Gray, fontWeight = FontWeight.Bold)
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(50.dp))
                        .background(if (isAiMode) MaterialTheme.colorScheme.primary else Color.Transparent)
                        .clickable { isAiMode = true }
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.AutoAwesome, "AI", modifier = Modifier.size(16.dp), tint = if (isAiMode) Color.White else Color.Gray)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("AI-Assisted", color = if (isAiMode) Color.White else Color.Gray, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(visible = isAiMode) {
                Column {
                    OutlinedTextField(
                        value = aiPrompt,
                        onValueChange = { aiPrompt = it },
                        modifier = Modifier.fillMaxWidth().height(150.dp),
                        placeholder = { Text("Describe what help you need in plain language...\ne.g. 'We need 5 people with medical experience at the downtown shelter immediately due to flooding.'") },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha=0.5f),
                            focusedBorderColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    GradientButton(text = "Generate Details", onClick = { /* Call Gemini */ }, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(24.dp))
                    Divider(color = Color.DarkGray)
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            // Manual Fields (or Auto-filled)
            OutlinedTextField(
                value = taskTitle,
                onValueChange = { taskTitle = it },
                label = { Text("Task Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text("Urgency Level", color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                UrgencyCard("LOW", Color(0xFF27AE60), urgency == "LOW") { urgency = "LOW" }
                UrgencyCard("MEDIUM", Color(0xFFF39C12), urgency == "MEDIUM") { urgency = "MEDIUM" }
                UrgencyCard("HIGH", Color(0xFFE74C3C), urgency == "HIGH") { urgency = "HIGH" }
                UrgencyCard("CRITICAL", Color(0xFFC0392B), urgency == "CRITICAL") { urgency = "CRITICAL" }
            }

            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(value = "", onValueChange = {}, label = { Text("Description") }, modifier = Modifier.fillMaxWidth().height(100.dp))
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = "", onValueChange = {}, label = { Text("Location (Search)") }, modifier = Modifier.fillMaxWidth())
            
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(value = "1", onValueChange = {}, label = { Text("Volunteers") }, modifier = Modifier.weight(1f))
                OutlinedTextField(value = "2.0", onValueChange = {}, label = { Text("Est. Hours") }, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Damage Photos (For AI Analysis)", color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .clickable { /* open gallery */ },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Filled.Add, null, tint = Color.Gray)
                    Text("Add Photo", color = Color.Gray, style = MaterialTheme.typography.labelSmall)
                }
            }

            Spacer(modifier = Modifier.height(48.dp))
            
            GradientButton(
                text = "Post Task & Match",
                onClick = onNavigateBack, // Mock success
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun RowScope.UrgencyCard(label: String, color: Color, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) color.copy(alpha=0.2f) else MaterialTheme.colorScheme.surface)
            .border(2.dp, if (isSelected) color else Color.Transparent, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = if (isSelected) color else Color.Gray, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
    }
}
