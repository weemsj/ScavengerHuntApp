package com.example.scavengerhuntapp.data

/**
 *  Assignment - 6: Mobile Treasure Hunt
 *
 *  Jacqueline Weems
 *
 *  Oregon State University -CS 429
 *  weemsj@oregonstate.edu
 *
 *  ScavengerHuntUiState.kt
 *
 */

import com.example.scavengerhuntapp.model.Clue

/**
 *  @constructor initializes [ScavengerHuntUiState]
 */

data class ScavengerHuntUiState(
    val currentClue: Clue = Clue(clue = 0, hint = 0, solution = 0, photo = 0, factOne = 0, factTwo = 0, factThree = 0, factFour = 0, lat = 0.0, lon = 0.0),
    val gameWon: Boolean = false,
    val nextClue: Clue = Clue(clue = 0, hint = 0, solution = 0, photo = 0, factOne = 0, factTwo = 0, factThree = 0, factFour = 0, lat = 0.0, lon = 0.0),
    val timer: Long = 0,
    val showDialog: Boolean = false,
    val loading: Boolean = true,
    val countDown: Int = 7,
    val foundButtonClicked:Boolean = false
  )