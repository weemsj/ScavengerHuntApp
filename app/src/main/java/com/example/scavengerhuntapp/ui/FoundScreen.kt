package com.example.scavengerhuntapp.ui

/**
 *  Assignment - 6: Mobile Treasure Hunt
 *
 *  Jacqueline Weems
 *
 *  Oregon State University -CS 429
 *  weemsj@oregonstate.edu
 *
 *  FoundScreen.kt
 *
 */

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.scavengerhuntapp.R
import com.example.scavengerhuntapp.data.ClueList
import com.example.scavengerhuntapp.model.Clue
import com.example.scavengerhuntapp.ui.theme.ScavengerHuntAppTheme

/**
 * Screen shown when a location is successfully found, the timer is also stopped on this page allowing
 * user to read information about the location. If the game is over shows a congratulations message is
 * also displayed.
 *
 * @param onContinue
 * @param modifier
 * @param clue
 * @param timer
 * @param gameOver
 * @param buttonText
 */
@Composable
fun FoundScreen(
    onContinue: () -> Unit,
    modifier: Modifier = Modifier,
    clue: Clue,
    timer: String,
    gameOver: Boolean,
    buttonText: Int
) {

    BackgroundPic(modifier = modifier.blur(radiusX = 10.dp, radiusY = 10.dp))
    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.2f)
                .padding(start = 50.dp)
        ) {
            TimerContainer(timer = timer)
        }
        Row(
            modifier = Modifier
                .fillMaxHeight(.9f)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxSize()
            ) {
                FoundClue(onClick = onContinue, clue = clue, buttonText = buttonText)
            }
        }
    }
    if (gameOver) {
        Column {
            Row {
                Text(
                    text = stringResource(id = R.string.congrats),
                    style = MaterialTheme.typography.headlineLarge,
                    color = colorResource(id = R.color.white),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp, top = 20.dp)
                        .fillMaxWidth()
                )
            }
            Row {
                Text(
                    text = stringResource(id = R.string.congrats_details),
                    style = MaterialTheme.typography.headlineSmall,
                    color = colorResource(id = R.color.white),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp)
                )
            }
        }
    }
}

/**
 * Card composable that shows Location information
 * @param onClick
 * @param clue
 * @param buttonText
 */
@Composable
fun FoundClue(onClick: () -> Unit, clue: Clue, buttonText: Int) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        modifier = Modifier
            .fillMaxWidth(.90f)
            .fillMaxHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = clue.photo),
                contentDescription = stringResource(id = clue.solution),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()

            )
        }
        Row(modifier = Modifier.padding(start = 12.dp, bottom = 12.dp)) {
            Text(
                text = stringResource(id = clue.solution),
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Column(verticalArrangement = Arrangement.Top) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, bottom = 8.dp, end = 12.dp)
            ) {
                Text(
                    text = stringResource(id = clue.factOne),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, bottom = 8.dp, end = 12.dp)
            ) {
                Text(
                    text = stringResource(id = clue.factTwo),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, bottom = 8.dp, end = 12.dp)
            ) {
                Text(
                    text = stringResource(id = clue.factThree),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, bottom = 8.dp, end = 12.dp)
            ) {
                Text(
                    text = stringResource(id = clue.factFour),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        ) {

            ContinueButton(onClick = onClick, text = buttonText)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FoundScreenPreview() {
    ScavengerHuntAppTheme {
        FoundScreen(
            onContinue = { /*TODO*/ },
            clue = ClueList.clue[1],
            modifier = Modifier.fillMaxWidth(),
            timer = "00:00:00",
            gameOver = false,
            buttonText = R.string.next_clue
        )
    }
}