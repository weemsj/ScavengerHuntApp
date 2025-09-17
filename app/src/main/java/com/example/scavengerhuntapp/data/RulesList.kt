package com.example.scavengerhuntapp.data

/**
 *  Assignment - 6: Mobile Treasure Hunt
 *
 *  Jacqueline Weems
 *
 *  Oregon State University -CS 429
 *  weemsj@oregonstate.edu
 *
 *  RulesList.kt
 *
 */

import com.example.scavengerhuntapp.R
import com.example.scavengerhuntapp.model.Rule

/**
 * [RulesList] holds one val [rules] which is a listOf[Rule]
 */
object RulesList{
    val rules: List<Rule> = listOf(
        Rule(R.string.rule_1),
        Rule(R.string.rule_2),
        Rule(R.string.rule_3),
        Rule(R.string.rule_4),
        Rule(R.string.rule_5),
        Rule(R.string.note)
    )
}

