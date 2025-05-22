package com.example.stillyou.menu

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

/**
 * Composable function for the main menu of the application.
 * Displays a list of game options for the user to choose from.
 *
 * @param onGameSelected Lambda function to be invoked when a game option is selected.
 *                       It receives the name of the selected game as a String.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenu(role: UserRole, navController: NavController, onGameSelected: (String) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Main Menu") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (role) {
                UserRole.Doctor, UserRole.Patient -> {
                    Text("Choose Your Game", fontSize = 24.sp, modifier = Modifier.padding(bottom = 32.dp))
                    val games = listOf("Memory Match")
                    games.forEach { gameName ->
                        GameMenuItem(gameName = gameName) {
                            onGameSelected(gameName)
                        }
                    }
                }
                UserRole.Guardian -> {
                    Text("Assign a Game to Patient", fontSize = 24.sp, modifier = Modifier.padding(bottom = 32.dp))
                    val patients = listOf("Patient A", "Patient B")
                    val games = listOf("Memory Match")
                    patients.forEach { patient ->
                        Text(text = patient)
                        games.forEach { gameName ->
                            Button(
                                onClick = { onGameSelected(gameName) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Text(text = "Assign \"$gameName\" to $patient")
                            }
                        }
                    }
                }
            }
        }
    }
}


/**
 * Composable function for a single game menu item.
 * Displays a button with the game's name.
 *
 * @param gameName The name of the game to display.
 * @param onClick Lambda function to be invoked when the button is clicked.
 */
@Composable
fun GameMenuItem(gameName: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = gameName)
    }
}

@Preview(showBackground = true)
@Composable
fun MainMenuPreview() {
    val navController = rememberNavController()
    MainMenu(role = UserRole.Patient, navController = navController, onGameSelected = {})
}
