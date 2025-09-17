package com.example.scavengerhuntapp.data

/**
 *  Assignment - 6: Mobile Treasure Hunt
 *
 *  Jacqueline Weems
 *
 *  Oregon State University -CS 429
 *  weemsj@oregonstate.edu
 *
 *  ClueList.kt
 *
 */

import com.example.scavengerhuntapp.R
import com.example.scavengerhuntapp.model.Clue

/**
 * [ClueList] holds one val [clue] which is a listOf[Clue]
 */
object ClueList {
    val clue: List<Clue> = listOf(
        Clue(R.string.clue_1, R.string.hint_1, R.string.answer_1, R.drawable.eiffel_tower, R.string.eiffel_1, R.string.eiffel_2, R.string.eiffel_3, R.string.eiffel_4, lat = 48.8584, lon = 2.2945),
        Clue(R.string.clue_2, R.string.hint_2, R.string.answer_2, R.drawable.colosseum, R.string.colosseum_1, R.string.colosseum_2, R.string.colosseum_3, R.string.colosseum_4, 41.8902, 12.4922)
    )
}