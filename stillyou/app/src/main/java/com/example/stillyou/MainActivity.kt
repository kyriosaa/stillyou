package com.example.stillyou

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.stillyou.games.MemoryMatch
import com.example.stillyou.menu.MainMenu
import com.example.stillyou.menu.UserRole
import com.example.stillyou.menu.WelcomeMenu
import com.example.stillyou.ui.theme.StillyouTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StillyouTheme {
                AppNavigation() // navigation composable
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeMenu(navController = navController, onRoleSelected = { /* handled in navigate */ })
        }

        composable(
            route = "main_menu/{role}",
            arguments = listOf(navArgument("role") { type = NavType.StringType })
        ) { backStackEntry ->
            val roleName = backStackEntry.arguments?.getString("role") ?: "Patient"
            val role = try {
                UserRole.valueOf(roleName)
            } catch (e: IllegalArgumentException) {
                UserRole.Patient
            }

            MainMenu(
                role = role,
                navController = navController,
                onGameSelected = { gameName ->
                    when (gameName) {
                        "Memory Match" -> navController.navigate("memory_match")   // navigates to MemoryMatch.kt
                        else -> Log.d("MainMenu", "Game not implemented: $gameName")
                    }
                }
            )
        }

        composable("memory_match") {
            // MemoryMatch game composable
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                MemoryMatch(
                    onGameEnd = { timeTakenMillis ->
                        Log.d("MemoryGame", "Game ended! Time taken: ${timeTakenMillis / 1000} seconds")
                    },
                    modifier = Modifier.padding(innerPadding),
                    onBackClick = { navController.popBackStack() } // handle back button click
                )
            }
        }

        // we can add other game destinations here
        // composable("another_game_screen") { AnotherGameScreen() }

        composable("login") {
            // TODO: Login screen composable
        }

        composable("register") {
            // TODO: Register screen composable
        }
    }
}
