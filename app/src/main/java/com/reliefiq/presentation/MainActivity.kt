package com.reliefiq.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.reliefiq.presentation.components.AnimatedBottomNav
import com.reliefiq.presentation.components.BottomNavItem
import com.reliefiq.presentation.screens.auth.BiometricScreen
import com.reliefiq.presentation.screens.auth.LoginScreen
import com.reliefiq.presentation.screens.auth.RegisterScreen
import com.reliefiq.presentation.screens.leaderboard.LeaderboardScreen
import com.reliefiq.presentation.screens.map.LiveMapScreen
import com.reliefiq.presentation.screens.ngo.DashboardScreen
import com.reliefiq.presentation.screens.ngo.ImpactReportScreen
import com.reliefiq.presentation.screens.ngo.PostTaskScreen
import com.reliefiq.presentation.screens.ngo.VolunteerTrackerScreen
import com.reliefiq.presentation.screens.onboarding.OnboardingScreen
import com.reliefiq.presentation.screens.settings.SettingsScreen
import com.reliefiq.presentation.screens.sos.SOSScreen
import com.reliefiq.presentation.screens.splash.SplashScreen
import com.reliefiq.presentation.screens.volunteer.MyContributionsScreen
import com.reliefiq.presentation.screens.volunteer.ProfileSetupScreen
import com.reliefiq.presentation.screens.volunteer.TaskDetailScreen
import com.reliefiq.presentation.screens.volunteer.TaskListScreen
import com.reliefiq.presentation.theme.ReliefIQTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        
        setContent {
            ReliefIQTheme(darkTheme = true) {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val bottomNavItems = listOf(
                    BottomNavItem("Tasks", Icons.Filled.List, "task_list"),
                    BottomNavItem("Map", Icons.Filled.Map, "live_map"),
                    BottomNavItem("Leaderboard", Icons.Filled.EmojiEvents, "leaderboard"),
                    BottomNavItem("Profile", Icons.Filled.Person, "contributions"),
                    BottomNavItem("Settings", Icons.Filled.Settings, "settings")
                )

                val showBottomNav = currentRoute in listOf("task_list", "live_map", "contributions", "dashboard", "leaderboard", "settings")

                Scaffold(
                    bottomBar = {
                        if (showBottomNav) {
                            AnimatedBottomNav(
                                items = bottomNavItems,
                                currentRoute = currentRoute,
                                onItemClick = { item ->
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                ) { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = "splash",
                        modifier = Modifier.padding(padding)
                    ) {
                        composable("splash") { SplashScreen({ navController.navigate("onboarding") }, { navController.navigate("task_list") }, false) }
                        composable("onboarding") { OnboardingScreen { navController.navigate("login") } }
                        composable("login") { LoginScreen({ navController.navigate("register") }, { navController.navigate("task_list") }, { navController.navigate("biometric") }, true) }
                        composable("register") { RegisterScreen({ navController.navigate("profile_setup") }, { navController.navigate("dashboard") }, { navController.navigate("login") }) }
                        composable("biometric") { BiometricScreen({ navController.navigate("task_list") }, { navController.navigate("login") }) }
                        
                        composable("profile_setup") { ProfileSetupScreen { navController.navigate("task_list") } }
                        composable("task_list") { TaskListScreen({ taskId -> navController.navigate("task_detail/$taskId") }, { navController.navigate("sos") }) }
                        composable("task_detail/{taskId}") { backStackEntry ->
                            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
                            TaskDetailScreen(taskId, { navController.popBackStack() }, { navController.popBackStack() })
                        }
                        composable("contributions") { MyContributionsScreen() }
                        composable("sos") { SOSScreen { navController.popBackStack() } }
                        
                        composable("live_map") { LiveMapScreen() }
                        composable("post_task") { PostTaskScreen { navController.popBackStack() } }
                        composable("dashboard") { DashboardScreen { navController.navigate("post_task") } }
                        composable("tracker") { VolunteerTrackerScreen() }
                        composable("report") { ImpactReportScreen() }
                        
                        composable("leaderboard") { LeaderboardScreen() }
                        composable("settings") { SettingsScreen { navController.navigate("login") { popUpTo(0) } } }
                    }
                }
            }
        }
    }
}
