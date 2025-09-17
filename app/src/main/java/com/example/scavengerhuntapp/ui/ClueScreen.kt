package com.example.scavengerhuntapp.ui

/**
 *  Assignment - 6: Mobile Treasure Hunt
 *
 *  Jacqueline Weems
 *
 *  Oregon State University -CS 429
 *  weemsj@oregonstate.edu
 *
 *  ClueScreen.kt
 *
 */

import android.Manifest
import android.annotation.SuppressLint
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.scavengerhuntapp.Geo
import com.example.scavengerhuntapp.R
import com.example.scavengerhuntapp.checkCoords
import com.example.scavengerhuntapp.data.ClueList
import com.example.scavengerhuntapp.data.RulesList
import com.example.scavengerhuntapp.getLocation
import com.example.scavengerhuntapp.model.Clue
import com.example.scavengerhuntapp.ui.theme.ScavengerHuntAppTheme
import com.example.scavengerhuntapp.ui.viewModel.ScavengerHuntViewModel
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


/**
 * Shows the Clue that corrisponds to a actual location, with a hint button
 * @suppress Assumes user has the necessary location permissions
 *
 * @param modifier
 * @param gameModel
 * @param clue
 * @param onFoundIt
 * @param timer
 * @param onQuitGame
 * @param isLocationGranted
 * @param isPreciseLocation
 */
@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClueScreen(
    modifier: Modifier = Modifier,
    gameModel: ScavengerHuntViewModel = viewModel(),
    clue: Clue,
    onFoundIt: () -> Unit,
    timer: String,
    onQuitGame: () -> Unit,
    isPreciseLocation: Boolean,
    isLocationGranted: Boolean,
) {
    var loading by rememberSaveable {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember {
        mutableStateOf(false)
    }


    Scaffold(// button at the bottom of screen that controls the modal screen
        floatingActionButton = {
            OpenButton(onClick = { showBottomSheet = true }, R.string.hint)
        },
        modifier = Modifier.fillMaxSize()
    ) { contentPadding ->
        BackgroundPic(modifier = modifier.blur(radiusX = 10.dp, radiusY = 10.dp))
        Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.1f)
                    .padding(start = 50.dp)
            ) {
                TimerContainer(timer = timer)
            }
            Row(
                modifier = Modifier
                    .fillMaxHeight(.7f)
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier.fillMaxSize()
                ) {
                    Clue(
                        clue = clue,
                        gameModel = gameModel,
                        onFoundIt = onFoundIt,
                        onQuitGame = onQuitGame,
                        isPreciseLocation = isPreciseLocation,
                        isLocationGranted = isLocationGranted,
                        loading = { loading = it },
                    )
                    if (loading) {
                        IndeterminateCircularIndicator()
                    }
                }
            }
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
                        modifier = modifier.padding(12.dp),
                        rules = RulesList.rules,
                        hint = clue.hint,
                        isRule = false
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
 * Fixes the game timer to the top of the Card
 * @param timer
 */
@Composable
fun TimerContainer(timer: String) {
    ElevatedCard(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
        shape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)
    )
    {

        Text(text = timer, modifier = Modifier.padding(5.dp))
    }
}

/**
 * Continue Button composable
 * @param onClick
 * @param text
 */
@Composable
fun ContinueButton(onClick: () -> Unit, text: Int) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSecondaryContainer)
    ) {
        Text(
            text = stringResource(id = text),
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.white)
        )
    }
}

/**
 * EndGameButton Composable
 * @param onClick
 */
@Composable
fun EndGameButton(onClick: () -> Unit) {
    OutlinedButton(onClick = onClick) {
        Text(text = stringResource(id = R.string.leave))
    }
}

/**
 * Displays Clue info Card from Lazy list
 * @param clue
 * @param onFoundIt
 * @param onQuitGame
 * @param isLocationGranted
 * @param isPreciseLocation
 * @param gameModel
 * @param loading
 */
@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)
@Composable
fun Clue(
    clue: Clue,
    onFoundIt: () -> Unit,
    onQuitGame: () -> Unit,
    isPreciseLocation: Boolean,
    isLocationGranted: Boolean,
    gameModel: ScavengerHuntViewModel = viewModel(),
    loading: (Boolean) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val locationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val gameState by gameModel.uiState.collectAsState()

    var location by remember {
        mutableStateOf(Pair(0.0, 0.0))
    }


    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        modifier = Modifier.fillMaxWidth(.80f)
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
                    .blur(radius = 30.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
            )
        }
        Row(modifier = Modifier.padding(start = 12.dp, bottom = 12.dp)) {
            Text(
                text = stringResource(id = R.string.clue),
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, bottom = 24.dp, end = 12.dp)
        ) {
            Text(
                text = stringResource(id = clue.clue),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        ) {
            Column(Modifier.padding(end = 20.dp)) {
                ContinueButton(
                    onClick = {
                        if (isLocationGranted) {
                            //To get more accurate or fresher device location use this method
                            getLocation(
                                location = { location = it },
                                scope = scope,
                                locationProviderClient = locationClient,
                                isPreciseLocation = isPreciseLocation
                            )
                            gameModel.foundButtonClicked()
                        } else {
                            onFoundIt()
                        }
                    },
                    text = R.string.found_it
                )
            }
            Column {
                EndGameButton(onClick = onQuitGame)
            }
        }

        if (gameState.foundButtonClicked) {
            loading(true)
            LaunchedEffect(gameModel) {
                coroutineScope {
                    launch { gameModel.loadDelay() }
                }
            }
        }


        if (!gameState.loading) {
            CheckGuess(
                clueLat = clue.lat,
                clueLon = clue.lon,
                currentLocation = location,
                foundIt = onFoundIt,
                gameModel = gameModel,
                location = { location = it },
            )
        }

        if (gameState.showDialog) {
            Dialog(onDismissRequest = { gameModel.dismissAlertDialog() }) {
                Card(colors = CardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer, contentColor = MaterialTheme.colorScheme.onSecondaryContainer, disabledContainerColor = MaterialTheme.colorScheme.inverseOnSurface, disabledContentColor = MaterialTheme.colorScheme.inverseOnSurface),) {
                    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(20.dp).fillMaxHeight(.4f)) {
                        Row(modifier = Modifier.padding(bottom = 16.dp)) {
                            Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
                        }
                        Row(modifier = Modifier.padding(10.dp)) {
                            Text(text = stringResource(id = R.string.wrong_location), style = MaterialTheme.typography.headlineSmall)
                        }
                        Row(modifier = Modifier.padding(bottom = 24.dp)) {
                            Text(text = stringResource(id = R.string.incorrect), style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
                        }
                        Button(onClick = { gameModel.dismissAlertDialog() }) {
                            Text(text = stringResource(id = R.string.dismiss))
                        }
                    }
                }
            }


        }
    }
}

/**
 * Converts [location],[clueLat],[clueLon] into [Geo] Checks Haversine.kt for correct location.
 *
 * @param clueLat
 * @param clueLon
 * @param currentLocation
 * @param foundIt
 * @param gameModel
 * @param location
 */
@Composable
fun CheckGuess(
    clueLat: Double,
    clueLon: Double,
    currentLocation: Pair<Double, Double>,
    foundIt: () -> Unit,
    gameModel: ScavengerHuntViewModel,
    location: (Pair<Double, Double>) -> Unit,
) {

    if (checkCoords(
            Geo(clueLat, clueLon),
            Geo(currentLocation.first, currentLocation.second)
        ) <= 1
    ) {
        foundIt()
        gameModel.blockLocationCheck()
    } else {
        gameModel.showAlertDialog()
        gameModel.blockLocationCheck()
        location(Pair(0.0, 0.0))
    }

}


@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)
@Preview(showBackground = true)
@Composable
fun ClueScreenPreview() {
    ScavengerHuntAppTheme {
        ClueScreen(
            modifier = Modifier.fillMaxSize(),
            clue = ClueList.clue[1],
            onFoundIt = {},
            timer = "00:00:00",
            onQuitGame = {},
            isPreciseLocation = false,
            isLocationGranted = false,
        )
    }
}

