package com.example.scavengerhuntapp.ui.viewModel

/**
 *  Assignment - 6: Mobile Treasure Hunt
 *
 *  Jacqueline Weems
 *
 *  Oregon State University -CS 429
 *  weemsj@oregonstate.edu
 *
 *  ScavengerHuntViewModel.kt
 *
 */

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scavengerhuntapp.data.ClueList
import com.example.scavengerhuntapp.data.ScavengerHuntUiState
import com.example.scavengerhuntapp.model.Clue
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScavengerHuntViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ScavengerHuntUiState())
    val uiState : StateFlow<ScavengerHuntUiState> = _uiState.asStateFlow()

    private lateinit var currentClue: Clue
    private var usedClue: MutableSet<Clue> = mutableSetOf()

    private var countDown by mutableIntStateOf(uiState.value.countDown)


    /**
     *  private function pick a Clue to be used in the Scavenger Hunt Game @return [Clue]
     */
    private fun pickAClue(): Clue {
        currentClue = ClueList.clue.random()
        if (usedClue.contains(currentClue)) {
            return pickAClue()
        }else{
            usedClue.add(currentClue)
            return currentClue
        }
    }

    /**
     *  Checks to se if there are more clues to be found, if not update [_uiState] gameWon Boolean true
     */
    fun foundClue() {
        if (usedClue.size == ClueList.clue.size) {
            _uiState.update { currentState ->
                currentState.copy(
                    gameWon = true,
                )
            }
        }
    }

    /**
     *  Calls [pickAClue] and updates [_uiState] currentClue to Clue returned form [pickAClue]
     */
    fun nextClue() {
        _uiState.update { currentState ->
            currentState.copy(
                currentClue = pickAClue()
            )
        }
    }

    /**
     * Resets the [_uiState]
     */
    fun resetGame(){
        usedClue.clear()
        _uiState.value = ScavengerHuntUiState(
            currentClue = pickAClue(),
            gameWon = false,
            timer = 0,
            showDialog = false,
            loading = true,
            foundButtonClicked = false
        )
        timerJob?.cancel()
    }


    init {
        resetGame()
    }


    private var timerJob: Job? = null

    /**
     * Starts a timer job
     */
    fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _uiState.update { currentTime ->
                    currentTime.copy(
                        timer = uiState.value.timer + 1
                    )
                }
            }
        }
    }

    /**
     * Dismiss alert dialog
     */
    fun dismissAlertDialog(){
        _uiState.update { it.copy(showDialog = false) }
    }

    /**
     * Shows alert dialog
     */
    fun showAlertDialog(){
        _uiState.update { it.copy(showDialog = true) }
    }

    /**
     * Pauses Timer
     */
    fun pauseTimer() {
        timerJob?.cancel()
    }

    /**
     * Cancels timer job
     */
    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }

    /**
     *  Resumes timer
     */
    private fun resume(){
        startTimer()
        _uiState.update { it.copy(loading = false, foundButtonClicked = false) }
        countDown = 5
    }

    /**
     *  Shows user visual indication the application is loading while keeping the app from
     *  recomposing while waiting on location information
     */
    suspend fun loadDelay(){
        pauseTimer()
        while (countDown > 0){
            delay(1000L)
            countDown -= 1
        }
        resume()
    }

    /**
     * Updates [_uiState] foundButtonClicked Boolean
     */
    fun foundButtonClicked(){
        _uiState.update { it.copy(foundButtonClicked = true) }
    }

    /**
     * Blocks UI from recomposing
     */
    fun blockLocationCheck(){
        _uiState.update { it.copy(loading = true) }
    }

}