package com.reliefiq.presentation.screens.volunteer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.reliefiq.core.utils.Resource
import com.reliefiq.data.model.Task
import com.reliefiq.data.model.UrgencyLevel
import com.reliefiq.presentation.components.GlassCard
import com.reliefiq.presentation.components.LottieLoader
import com.reliefiq.presentation.components.PulsingSOSButton
import com.reliefiq.presentation.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    onNavigateToDetail: (String) -> Unit,
    onTriggerSOS: () -> Unit,
    viewModel: TaskViewModel = hiltViewModel()
) {
    val tasksState by viewModel.tasks.collectAsState()
    var isOnline by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Ready to help, Rahul?", fontWeight = FontWeight.Bold)
                        Text("Mumbai • 1500 XP", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    }
                },
                actions = {
                    Switch(checked = isOnline, onCheckedChange = { isOnline = it })
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            PulsingSOSButton(
                modifier = Modifier.size(80.dp),
                onTrigger = onTriggerSOS
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when (tasksState) {
                is Resource.Loading -> {
                    LottieLoader(modifier = Modifier.align(Alignment.Center))
                }
                is Resource.Error -> {
                    Text(
                        text = "Error loading tasks",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is Resource.Success -> {
                    val tasks = (tasksState as Resource.Success<List<Task>>).data
                    if (tasks.isEmpty()) {
                        Text(
                            text = "No active tasks right now",
                            color = Color.Gray,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            item {
                                Text("All Active Tasks", style = MaterialTheme.typography.headlineSmall, color = Color.White)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                            
                            items(tasks) { task ->
                                TaskCard(task = task, onClick = { onNavigateToDetail(task.id) })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TaskCard(task: Task, onClick: () -> Unit) {
    val urgencyColor = when(task.urgencyLevel) {
        UrgencyLevel.CRITICAL -> Color(0xFFC0392B)
        UrgencyLevel.HIGH -> Color(0xFFE74C3C)
        UrgencyLevel.MEDIUM -> Color(0xFFF39C12)
        UrgencyLevel.LOW -> Color(0xFF27AE60)
    }

    GlassCard(onClick = onClick) {
        Row(modifier = Modifier.fillMaxWidth()) {
            // Urgency Strip
            Box(modifier = Modifier.width(4.dp).height(80.dp).background(urgencyColor))
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(text = task.title, fontWeight = FontWeight.Bold, color = Color.White)
                Text(text = "By ${task.createdBy}", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    task.requiredSkills.take(3).forEach { skill ->
                        Surface(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(4.dp)) {
                            Text(skill, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), color = Color.LightGray)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                LinearProgressIndicator(
                    progress = { if (task.volunteersNeeded > 0) task.volunteersAccepted.toFloat() / task.volunteersNeeded else 0f },
                    modifier = Modifier.fillMaxWidth().height(4.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                Text("${task.volunteersAccepted}/${task.volunteersNeeded} Volunteers", style = MaterialTheme.typography.labelSmall, color = Color.Gray, modifier = Modifier.padding(top=2.dp))
            }
        }
    }
}
