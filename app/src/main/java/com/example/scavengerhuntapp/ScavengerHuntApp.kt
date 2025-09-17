package com.example.scavengerhuntapp

/**
 *  Assignment - 6: Mobile Treasure Hunt
 *
 *  Jacqueline Weems
 *
 *  Oregon State University -CS 429
 *  weemsj@oregonstate.edu
 *
 *  ScavengerHuntApp.kt
 *
 */

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scavengerhuntapp.data.RulesList
import com.example.scavengerhuntapp.ui.ClueScreen
import com.example.scavengerhuntapp.ui.FoundScreen
import com.example.scavengerhuntapp.ui.StartScreen
import com.example.scavengerhuntapp.ui.viewModel.ScavengerHuntViewModel

/**
 *  @property Start
 *  @property Clue
 *  @property Found
 *  Enum class for Navigation between screens
 */
enum class ScavengerHuntScreen {
    Start,
    Clue,
    Found,
}

/**
 *  Long type extension function that converts a Long Double to a string format for readability
 *
 *  @return String
 */
fun Long.formatTime(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val remainingSeconds = this % 60
    return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
}

/**
 * Application Navigator
 * @param isLocationGranted
 * @param isPreciseLocation
 */
@SuppressLint("MissingPermission")
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun ScavengerHuntApp(
    isLocationGranted: Boolean,
    isPreciseLocation: Boolean,
    gameModel: ScavengerHuntViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
) {

    Scaffold { innerPadding ->
        val gameUiState = gameModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = ScavengerHuntScreen.Start.name,
            modifier = Modifier.padding(innerPadding)

        ) {
            composable(ScavengerHuntScreen.Start.name) {
                StartScreen(
                    rules = RulesList.rules,
                    onStartGame = {
                        gameModel.startTimer()
                        navController.navigate(ScavengerHuntScreen.Clue.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(ScavengerHuntScreen.Clue.name) {

                ClueScreen(
                    clue = gameUiState.value.currentClue,
                    modifier = Modifier.fillMaxSize(),
                    gameModel = gameModel,
                    onFoundIt = {
                        gameModel.foundClue()
                        gameModel.pauseTimer()
                        // if all clues are found navigate to Congrats Page else reload Clue page with next clue
                        navController.navigate(ScavengerHuntScreen.Found.name)
                    },
                    onQuitGame = {
                        // reset the game and navigate to start screen
                        gameModel.resetGame()
                        navController.navigate(ScavengerHuntScreen.Start.name)
                    },
                    timer = gameUiState.value.timer.formatTime(),
                    isLocationGranted = isLocationGranted,
                    isPreciseLocation = isPreciseLocation,
                )
            }
            composable(ScavengerHuntScreen.Found.name) {
                FoundScreen(
                    onContinue = if (!gameUiState.value.gameWon) {
                        {
                            gameModel.nextClue()
                            gameModel.startTimer()
                            navController.navigate(ScavengerHuntScreen.Clue.name)
                        }
                    } else {
                        {
                            gameModel.resetGame()
                            navController.navigate(ScavengerHuntScreen.Start.name)
                        }
                    },
                    gameOver = gameUiState.value.gameWon,
                    clue = gameUiState.value.currentClue,
                    timer = gameUiState.value.timer.formatTime(),
                    modifier = Modifier.fillMaxSize(),
                    buttonText = if (gameUiState.value.gameWon) {
                        R.string.close
                    } else {
                        R.string.next_clue
                    }
                )
            }
        }
    }
}