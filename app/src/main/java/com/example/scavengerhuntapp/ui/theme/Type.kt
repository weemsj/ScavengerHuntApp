package com.example.scavengerhuntapp.ui.theme

/**
 *  Assignment - 6: Mobile Treasure Hunt
 *
 *  Jacqueline Weems
 *
 *  Oregon State University -CS 429
 *  weemsj@oregonstate.edu
 *
 *  Type.kt
 *
 */

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.scavengerhuntapp.R

val Roboto = FontFamily(
    Font(R.font.roboto_regular),
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_thin, FontWeight.Thin),
)

val Comforter = FontFamily(
    Font(R.font.comforter_regular)
)

val BebasNeue = FontFamily(
    Font(R.font.bebas_neue_regular)
)

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Comforter,
        fontWeight = FontWeight.Normal,
        fontSize = 64.sp
    ),

    displayMedium = TextStyle(
        fontFamily = BebasNeue,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp
    ),

    displaySmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp
    ),

    headlineLarge = TextStyle(
        fontFamily = Comforter,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp
    ),

    headlineMedium = TextStyle(
        fontFamily = BebasNeue,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp
    ),

    headlineSmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),

    titleLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),

    titleMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),

    titleSmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        letterSpacing = 0.5.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    bodySmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Thin,
        fontSize = 14.sp
    )

)