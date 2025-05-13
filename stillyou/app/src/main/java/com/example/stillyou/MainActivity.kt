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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.stillyou.ui.theme.StillyouTheme
import com.example.stillyou.games.MemoryMatch
import com.example.stillyou.menu.MainMenu // Import the MainMenu composable

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

    NavHost(navController = navController, startDestination = "main_menu") {
        composable("main_menu") {
            MainMenu(
                onGameSelected = { gameName ->
                    when (gameName) {
                        "Card Game" -> navController.navigate("memory_match") // Navigate to the MemoryMatch screen
                        // Add cases for other games here
                        else -> Log.d("MainMenu", "Game not implemented: $gameName")
                    }
                }
            )
        }
        composable("memory_match") {
            // Your MemoryMatch game composable
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                MemoryMatch(
                    onGameEnd = { timeTakenMillis ->
                        Log.d("MemoryGame", "Game ended! Time taken: ${timeTakenMillis / 1000} seconds")
                        // You might want to navigate back to the main menu or a results screen here
                        // navController.popBackStack() // Example: navigate back
                    },
                    modifier = Modifier.padding(innerPadding), // Apply padding
                    onBackClick = { navController.popBackStack() } // Handle back button click
                )
            }
        }
        // Add other game destinations here
        // composable("another_game_screen") { AnotherGameScreen() }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    StillyouTheme {
//        Greeting("Android")
//    }
//}