package com.example.scavengerhuntapp.model

/**
 *  Assignment - 6: Mobile Treasure Hunt
 *
 *  Jacqueline Weems
 *
 *  Oregon State University -CS 429
 *  weemsj@oregonstate.edu
 *
 *  Rule.kt
 *
 */

import androidx.annotation.StringRes

/**
 * @constructor [Rule]
 */
data class Rule(
    @StringRes val rule: Int
)
