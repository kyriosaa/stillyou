package com.example.stillyou.menu

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Composable function for the main menu of the application.
 * Displays a list of game options for the user to choose from.
 *
 * @param onGameSelected Lambda function to be invoked when a game option is selected.
 *                       It receives the name of the selected game as a String.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenu(onGameSelected: (String) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Main Menu") })
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
            Text(
                text = "Choose Your Game",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // List of available games
            val games = listOf("Card Game") // add more games here

            games.forEach { gameName ->
                GameMenuItem(gameName = gameName) {
                    onGameSelected(gameName)
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
    MainMenu(onGameSelected = {})
}