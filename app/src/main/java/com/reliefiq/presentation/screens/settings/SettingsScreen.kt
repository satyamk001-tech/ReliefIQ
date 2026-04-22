package com.reliefiq.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onLogout: () -> Unit
) {
    var biometricEnabled by remember { mutableStateOf(false) }
    var locationHighAccuracy by remember { mutableStateOf(true) }
    var newTasksNotif by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
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
                .verticalScroll(rememberScrollState())
        ) {
            // Profile Header
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(64.dp).clip(CircleShape).background(Color.DarkGray))
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Rahul Sharma", color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                    Text("rahul@example.com", color = Color.Gray)
                }
                Icon(Icons.Filled.ChevronRight, "Edit", tint = Color.Gray)
            }

            Divider(color = MaterialTheme.colorScheme.surface)

            SettingsSection("Notifications") {
                SettingsSwitch("New Task Matches", newTasksNotif) { newTasksNotif = it }
                SettingsSwitch("SOS Alerts", true) {}
                SettingsSwitch("Task Updates", true) {}
            }

            SettingsSection("Location & Tracking") {
                SettingsSwitch("High Accuracy Tracking", locationHighAccuracy) { locationHighAccuracy = it }
                SettingsItem("Background Location Permission", "Granted") {}
            }

            SettingsSection("Security") {
                SettingsSwitch("Biometric Login", biometricEnabled) { biometricEnabled = it }
                SettingsItem("Change Password", "") {}
            }

            SettingsSection("Data & Privacy") {
                SettingsItem("Download My Data", "") {}
                SettingsItem("Delete Account", "", isDestructive = true) {}
            }

            SettingsSection("About") {
                SettingsItem("App Version", "1.0.0") {}
                SettingsItem("Privacy Policy", "") {}
            }

            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Text("Log Out", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SettingsSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(top = 24.dp)) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        content()
    }
}

@Composable
fun SettingsSwitch(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = Color.White)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun SettingsItem(label: String, value: String, isDestructive: Boolean = false, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = if (isDestructive) MaterialTheme.colorScheme.error else Color.White)
        if (value.isNotEmpty()) {
            Text(value, color = Color.Gray)
        } else {
            Icon(Icons.Filled.ChevronRight, null, tint = Color.Gray)
        }
    }
}
