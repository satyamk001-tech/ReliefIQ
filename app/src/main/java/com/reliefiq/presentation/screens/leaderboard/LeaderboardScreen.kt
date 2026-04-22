package com.reliefiq.presentation.screens.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.reliefiq.data.model.LeaderboardEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen() {
    var period by remember { mutableStateOf("ALL") }

    val mockEntries = listOf(
        LeaderboardEntry(uid = "1", xp = 15400, rank = 1),
        LeaderboardEntry(uid = "2", xp = 12200, rank = 2),
        LeaderboardEntry(uid = "3", xp = 11800, rank = 3),
        LeaderboardEntry(uid = "4", xp = 9500, rank = 4),
        LeaderboardEntry(uid = "me", xp = 4500, rank = 42) // Current user
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Global Leaderboard", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            
            // Period Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                listOf("WEEK", "MONTH", "ALL").forEach { p ->
                    val isSelected = period == p
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(50.dp))
                            .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
                            .clickable { period = p }
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(p, color = if (isSelected) Color.White else Color.Gray, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Top 3 Podium
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 16.dp)
                    .height(200.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                PodiumColumn(mockEntries[1], 2, Modifier.height(140.dp))
                PodiumColumn(mockEntries[0], 1, Modifier.height(180.dp))
                PodiumColumn(mockEntries[2], 3, Modifier.height(120.dp))
            }

            // List
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(mockEntries.drop(3)) { index, entry ->
                    val isMe = entry.uid == "me"
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isMe) MaterialTheme.colorScheme.primary.copy(alpha=0.15f) else MaterialTheme.colorScheme.surface)
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("${entry.rank}", color = Color.Gray, style = MaterialTheme.typography.titleMedium, modifier = Modifier.width(32.dp))
                        Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.DarkGray))
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(if (isMe) "You" else "Volunteer ${entry.rank}", color = Color.White, fontWeight = FontWeight.Bold)
                            Text(entry.badge, color = Color(0xFFF39C12), style = MaterialTheme.typography.bodySmall)
                        }
                        Text("${entry.xp} XP", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun PodiumColumn(entry: LeaderboardEntry, position: Int, modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier
    ) {
        Box(modifier = Modifier.size(56.dp).clip(CircleShape).background(Color.DarkGray))
        Spacer(modifier = Modifier.height(8.dp))
        Text("${entry.xp} XP", color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall)
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .width(80.dp)
                .weight(1f)
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .background(if (position == 1) Color(0xFFC0392B) else MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            Text("$position", color = Color.White, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Black)
        }
    }
}
