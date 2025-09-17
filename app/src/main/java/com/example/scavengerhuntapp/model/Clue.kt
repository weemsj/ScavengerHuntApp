package com.example.scavengerhuntapp.model

/**
 *  Assignment - 6: Mobile Treasure Hunt
 *
 *  Jacqueline Weems
 *
 *  Oregon State University -CS 429
 *  weemsj@oregonstate.edu
 *
 *  Clue.kt
 *
 */

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * @constructor [Clue]
 */

data class Clue(
    @StringRes val clue: Int,
    @StringRes val hint: Int,
    @StringRes val solution: Int,
    @DrawableRes val photo: Int,
    @StringRes val factOne: Int,
    @StringRes val factTwo: Int,
    @StringRes val factThree: Int,
    @StringRes val factFour: Int,
    val lat: Double,
    val lon: Double
)
