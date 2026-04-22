package com.reliefiq.presentation.screens.ngo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.reliefiq.presentation.components.GlassCard
import com.reliefiq.presentation.components.GradientButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImpactReportScreen() {
    var isGenerating by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Impact Report") },
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
            // Event Selector (Mock)
            OutlinedTextField(
                value = "Mumbai Floods 2024",
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Event") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Stats row
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                DashboardStatCard("Volunteers", "245", Modifier.weight(1f))
                DashboardStatCard("Hours", "1,200", Modifier.weight(1f))
                DashboardStatCard("Impacted", "~5K", Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // AI Narrative Section
            Text("AI Narrative", color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(selected = true, onClick = {}, label = { Text("Formal") })
                        FilterChip(selected = false, onClick = {}, label = { Text("Emotional") })
                        FilterChip(selected = false, onClick = {}, label = { Text("Data-Driven") })
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "During the recent floods, 245 ReliefIQ volunteers rapidly deployed across 12 sectors. Working over 1,200 cumulative hours, they resolved 90% of critical medical requests and successfully evacuated 5,000 residents before the second surge.",
                        color = Color.LightGray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Charts Section Placeholder
            Text("Charts", color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                Text("MPAndroidChart View", color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(48.dp))

            GradientButton(
                text = "Export PDF Report",
                isLoading = isGenerating,
                onClick = { isGenerating = true },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
