package com.example.stillyou

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.stillyou.ui.theme.StillyouTheme

import com.example.stillyou.games.MemoryMatch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StillyouTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Kin",
                        modifier = Modifier.padding(innerPadding)
                    )
                    MemoryMatch(
                        onGameEnd = { timeTakenMillis ->
                            // This lambda is called when the game ends
                            // You can add logic here to navigate to a results screen,
                            // show a dialog, or perform any action after the game finishes.
                            Log.d("MemoryGame", "Game ended! Time taken: ${timeTakenMillis / 1000} seconds")
                            // Example: You could update a state variable here to show a different screen
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StillyouTheme {
        Greeting("Android")
    }
}