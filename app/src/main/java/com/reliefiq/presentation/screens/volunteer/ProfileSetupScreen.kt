package com.reliefiq.presentation.screens.volunteer

import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import com.reliefiq.presentation.components.GradientButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileSetupScreen(
    onNavigateToHome: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 5 })
    val coroutineScope = rememberCoroutineScope()
    
    val selectedSkills = remember { mutableStateListOf<String>() }
    val allSkills = listOf("Medical", "Construction", "Driving", "Cooking", "Translation", "IT Support", "Counseling", "Logistics", "Search and Rescue", "First Aid", "Teaching", "Engineering")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        Column {
            // Animated Progress Bar
            LinearProgressIndicator(
                progress = { (pagerState.currentPage + 1) / 5f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surface
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f),
                userScrollEnabled = false
            ) { page ->
                when (page) {
                    0 -> StepPersonalInfo()
                    1 -> StepSkills(allSkills, selectedSkills) { skill ->
                        if (selectedSkills.contains(skill)) selectedSkills.remove(skill) else selectedSkills.add(skill)
                    }
                    2 -> StepLanguages()
                    3 -> StepAvailability()
                    4 -> StepEmergencyContact()
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (pagerState.currentPage > 0) {
                    TextButton(onClick = { coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) } }) {
                        Text("Back", color = Color.Gray)
                    }
                } else {
                    Spacer(modifier = Modifier.width(64.dp))
                }
                
                GradientButton(
                    text = if (pagerState.currentPage == 4) "Finish Setup" else "Next",
                    onClick = {
                        if (pagerState.currentPage < 4) {
                            coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1, animationSpec = spring()) }
                        } else {
                            // Save profile to Firestore
                            onNavigateToHome()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun StepPersonalInfo() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text("Personal Info", style = MaterialTheme.typography.headlineMedium, color = Color.White)
        Spacer(modifier = Modifier.height(32.dp))
        // Photo Circle Placeholder
        Box(modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface), contentAlignment = Alignment.Center) {
            Text("Tap to add\nPhoto", color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Phone Number") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("City") }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun StepSkills(skills: List<String>, selectedSkills: List<String>, onToggle: (String) -> Unit) {
    Column {
        Text("Your Skills", style = MaterialTheme.typography.headlineMedium, color = Color.White)
        Text("Select all that apply", color = Color.Gray)
        Spacer(modifier = Modifier.height(24.dp))
        LazyVerticalGrid(
            columns = GridCells.Adaptive(100.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(skills) { skill ->
                val isSelected = selectedSkills.contains(skill)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
                        .clickable { onToggle(skill) }
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(skill, color = if (isSelected) Color.White else Color.Gray, fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal)
                }
            }
        }
    }
}

@Composable
fun StepLanguages() {
    Column {
        Text("Languages", style = MaterialTheme.typography.headlineMedium, color = Color.White)
        // Similar to skills
    }
}

@Composable
fun StepAvailability() {
    Column {
        Text("Availability", style = MaterialTheme.typography.headlineMedium, color = Color.White)
    }
}

@Composable
fun StepEmergencyContact() {
    Column {
        Text("Emergency Contact", style = MaterialTheme.typography.headlineMedium, color = Color.White)
    }
}
