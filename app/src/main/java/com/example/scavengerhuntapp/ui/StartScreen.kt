package com.example.scavengerhuntapp.ui

/**
 *  Assignment - 6: Mobile Treasure Hunt
 *
 *  Jacqueline Weems
 *
 *  Oregon State University -CS 429
 *  weemsj@oregonstate.edu
 *
 *  StartScreen.kt
 *
 */

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.scavengerhuntapp.R
import com.example.scavengerhuntapp.data.RulesList
import com.example.scavengerhuntapp.model.Rule
import com.example.scavengerhuntapp.ui.theme.ScavengerHuntAppTheme
import kotlinx.coroutines.launch

/**
 * Start Screen that user sees before starting the game. Start button starts the game
 * View Rules modal shows the rules of the game.
 *
 * @param rules
 * @param onStartGame
 * @param modifier
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(
    rules: List<Rule>,
    onStartGame: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember {
        mutableStateOf(false)
    }

    Scaffold(
        // button at the bottom of screen that controls the modal screen
        floatingActionButton = {
            OpenButton(onClick = { showBottomSheet = true }, text = R.string.view_rules)
        },
        modifier = Modifier.fillMaxSize()

    ) { contentPadding ->

        // Start Screen Background image w/ saturation filter
        BackgroundPic(modifier)


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier.fillMaxSize()
        ) {

            // Application title card
            AppTitle(onStartGame = onStartGame)

            if (showBottomSheet) {
                ModalBottomSheet(
                    containerColor = colorResource(id = R.color.black),
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = sheetState
                )
                {

                    // Game rules LazyColumn List
                    ModalSheet(
                        contentPadding = contentPadding,
                        modifier = modifier,
                        rules = rules,
                        hint = 0,
                        isRule = true
                    )

                    CloseButton(
                        onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        },
                        modifier = Modifier.padding(12.dp)
                    )

                }
            }

        }

    }

}

/**
 * Custom AppTitle
 * @param onStartGame
 */
@Composable
private fun AppTitle(onStartGame: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(.95f)
            .fillMaxHeight(.5f),
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.transparent_black)
        )
    ) {
        // name of the game " game title "
        Row {
            Text(
                text = stringResource(id = R.string.name_1),
                style = MaterialTheme.typography.displayMedium,
                color = colorResource(id = R.color.white),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)

            )
        }
        Row {
            Text(
                text = stringResource(id = R.string.name_2),
                style = MaterialTheme.typography.displayLarge,
                color = colorResource(id = R.color.white),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 4.dp)

            )
        }
        Row {
            Text(
                text = stringResource(id = R.string.name_3),
                style = MaterialTheme.typography.displayMedium,
                color = colorResource(id = R.color.white),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        // Start Game button
        StartButton(onStartGame = onStartGame)
    }
}


/**
 * StartButton Composable
 * @param onStartGame
 */
@Composable
private fun StartButton(onStartGame: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(), contentAlignment = Alignment.Center
    ) {
        Button(onClick = onStartGame, modifier = Modifier.fillMaxWidth(.5f)) {
            Text(text = stringResource(id = R.string.start))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {

    ScavengerHuntAppTheme {
        StartScreen(rules = RulesList.rules, onStartGame = {}, modifier = Modifier.fillMaxSize())
    }
}