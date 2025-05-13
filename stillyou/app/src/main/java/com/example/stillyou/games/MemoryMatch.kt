package com.example.stillyou.games

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.flatMap
import com.example.stillyou.R
import kotlinx.coroutines.delay
import kotlin.random.Random

// Data class to represent a single card
data class Card(
    val id: Int,
    val imageResId: Int,
    var isFlipped: Boolean = false,
    var isMatched: Boolean = false
)

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MemoryMatch(onGameEnd: (timeTakenMillis: Long) -> Unit) {
    // State to hold the list of cards
    var cards by remember { mutableStateOf(generateCards()) }
    // State to keep track of the currently flipped cards
    var flippedCardIndices by remember { mutableStateOf(listOf<Int>()) }
    // State for the player's score (number of matched pairs)
    var score by remember { mutableStateOf(0) }
    // State to track if the game is over
    var isGameOver by remember { mutableStateOf(false) }
    // State to track the start time of the game
    var startTime by remember { mutableStateOf(System.currentTimeMillis()) }
    // State to track the time taken to complete the game
    var timeTaken by remember { mutableStateOf(0L) }

    // Effect to handle matching and flipping back cards
    LaunchedEffect(flippedCardIndices) {
        if (flippedCardIndices.size == 2) {
            // Disable clicks while checking for match
            val (index1, index2) = flippedCardIndices
            val card1 = cards[index1]
            val card2 = cards[index2]

            delay(1000) // Delay to allow the user to see the second card

            if (card1.imageResId == card2.imageResId) {
                // Match found
                cards = cards.toMutableList().apply {
                    this[index1] = this[index1].copy(isMatched = true)
                    this[index2] = this[index2].copy(isMatched = true)
                }
                score++
            } else {
                // No match, flip back
                cards = cards.toMutableList().apply {
                    this[index1] = this[index1].copy(isFlipped = false)
                    this[index2] = this[index2].copy(isFlipped = false)
                }
            }
            // Clear flipped cards
            flippedCardIndices = listOf()

            // Check if the game is over
            if (cards.all { it.isMatched }) {
                isGameOver = true
                timeTaken = System.currentTimeMillis() - startTime
                onGameEnd(timeTaken) // Call the callback when the game ends
            }
        }
    }

    // Composable for the game over screen
    @Composable
    fun GameOverScreen(timeTaken: Long) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Game Over!", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Time Taken: ${timeTaken / 1000} seconds", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(24.dp))
            // You could add a button to play again here
            Button(onClick = {
                // Reset the game
                cards = generateCards()
                flippedCardIndices = listOf()
                score = 0
                isGameOver = false
                startTime = System.currentTimeMillis() // Reset start time
            }) {
                Text("Play Again")
            }
        }
    }

    // Main game UI
    if (isGameOver) {
        GameOverScreen(timeTaken = timeTaken)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Score: $score", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            // Grid for the memory cards
            LazyVerticalGrid(
                columns = GridCells.Fixed(4), // Adjust the number of columns as needed
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(cards) { index, card ->
                    CardItem(card = card) {
                        // Handle card click
                        if (!card.isMatched && !card.isFlipped && flippedCardIndices.size < 2) {
                            cards = cards.toMutableList().apply {
                                this[index] = this[index].copy(isFlipped = true)
                            }
                            flippedCardIndices = flippedCardIndices + index
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardItem(card: Card, onCardClick: () -> Unit) {
    Card(
        modifier = Modifier
            .aspectRatio(1f) // Make cards square
            .clickable(enabled = !card.isMatched && !card.isFlipped) { onCardClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (card.isFlipped || card.isMatched) {
                // Display the image if flipped or matched
                Icon(
                    painter = painterResource(id = card.imageResId),
                    contentDescription = null, // Or a descriptive content description
                    modifier = Modifier.size(48.dp) // Adjust icon size
                )
            } else {
                // Display a back for unflipped cards
                Text("?", fontSize = 32.sp) // Simple question mark for the back
            }
        }
    }
}

// Function to generate a shuffled list of cards
fun generateCards(): List<Card> {
    val images = listOf(
        // drawable resource files
        R.drawable.ic_cat,
        R.drawable.ic_dog,
        R.drawable.ic_elephant,
        R.drawable.ic_frog,
        R.drawable.ic_giraffe,
        R.drawable.ic_lion,
        R.drawable.ic_panda,
        R.drawable.ic_penguin
    )

    // Create pairs of cards
    val cardList = images.flatMap { imageResId ->
        listOf(
            Card(id = Random.nextInt(), imageResId = imageResId),
            Card(id = Random.nextInt(), imageResId = imageResId)
        )
    }

    // Shuffle the cards
    return cardList.shuffled()
}

// Add some placeholder drawable resources if you don't have them yet
// In your res/drawable directory, create simple XML files or add image files.
// Example for icon_cat.xml:
/*
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24">
    <path
        android:fillColor="@android:color/white"
        android:pathData="M12,2C6.48,2 2,6.48 2,12s4.48,10 10,10 10,-4.48 10,-10S17.52,2 12,2zM12,20c-4.41,0 -8,-3.59 -8,-8s3.59,-8 8,-8 8,3.59 8,8 -3.59,8 -8,8zM10,9c-0.83,0 -1.5,0.67 -1.5,1.5S9.17,12 10,12s1.5,-0.67 1.5,-1.5S10.83,9 10,9zM14,9c-0.83,0 -1.5,0.67 -1.5,1.5S13.17,12 14,12s1.5,-0.67 1.5,-1.5S14.83,9 14,9zM12,17c-1.38,0 -2.63,-0.56 -3.54,-1.46c-0.2,-0.2 -0.2,-0.51 0,-0.71c0.2,-0.2 0.51,-0.2 0.71,0c0.75,0.75 1.75,1.17 2.83,1.17s2.08,-0.42 2.83,-1.17c0.2,-0.2 0.51,-0.2 0.71,0c0.2,0.2 0.2,0.51 0,0.71C14.63,16.44 13.38,17 12,17z" />
</vector>
*/