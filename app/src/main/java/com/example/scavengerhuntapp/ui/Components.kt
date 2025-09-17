package com.example.scavengerhuntapp.ui

/**
 *  Assignment - 6: Mobile Treasure Hunt
 *
 *  Jacqueline Weems
 *
 *  Oregon State University -CS 429
 *  weemsj@oregonstate.edu
 *
 *  Components.kt
 *
 */

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.scavengerhuntapp.R
import com.example.scavengerhuntapp.model.Rule
import kotlinx.coroutines.launch
import java.util.Timer
import kotlin.concurrent.schedule


/**
 *  Background Composable to make app cohesive
 *  @param modifier pass in a blur modifier to create some separation for [ClueScreen] and [FoundScreen]
 */
@Composable
fun BackgroundPic(modifier: Modifier) {
    Image(painter = painterResource(id = R.drawable.globe),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        colorFilter = ColorFilter.colorMatrix(
            ColorMatrix().apply { setToSaturation(.4f) }
        ),
        modifier = modifier.fillMaxSize()
    )
}

/**
 * ModalSheet used to display listOf[Rule] and [hint] to user
 * @param contentPadding
 * @param modifier
 * @param rules
 * @param hint
 * @param isRule
 */
@Composable
fun ModalSheet(
    contentPadding: PaddingValues,
    modifier: Modifier,
    rules: List<Rule>,
    hint: Int,
    isRule: Boolean
) {
    // Box with game rules
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.5f)
            .padding(contentPadding)
    ) {
        if (isRule) {
            Text(
                text = stringResource(id = R.string.rules_intro), color = colorResource(
                    id = R.color.white
                )
            )
        } else {
            Text(
                text = stringResource(id = R.string.hint),
                color = colorResource(id = R.color.white)
            )
        }

        Box(modifier = modifier) {
            if (isRule) {
                SheetContent(
                    rules = rules,
                    modifier = Modifier.padding(contentPadding),
                    hint = hint,
                    isRule = isRule
                )
            } else {
                SheetContent(
                    rules = rules,
                    modifier = Modifier.padding(contentPadding),
                    hint = hint,
                    isRule = isRule
                )
            }
        }
    }
}

/**
 * CloseButton composable
 * @param onClick
 * @param modifier
 */
@Composable
fun CloseButton(onClick: () -> Unit, modifier: Modifier) {
    Button(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(text = stringResource(id = R.string.close))
    }
}

/**
 * OpenButton Composable
 * @param onClick
 * @param text
 */
@Composable
fun OpenButton(onClick: () -> Unit, text: Int) {
    ExtendedFloatingActionButton(
        text = { Text(stringResource(id = text)) },
        icon = { Icon(imageVector = Icons.Filled.Add, contentDescription = "") },
        onClick = onClick
    )
}

/**
 * ScrollToTopButton Composable
 * @param onClick
 * @param modifier
 */
@Composable
fun ScrollToTopButton(onClick: () -> Unit, modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp), Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = { onClick() },
            containerColor = colorResource(id = R.color.white)
        )
        {
            Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = "Return to Top")
        }
    }

}

/**
 * SheetContent displays content inside ModalScreen
 * @param rules
 * @param modifier
 * @param hint
 * @param isRule
 */
@Composable
fun SheetContent(rules: List<Rule>, modifier: Modifier = Modifier, hint: Int, isRule: Boolean) {

    Box(modifier = modifier) {
        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        if (isRule) {
            LazyColumn(
                state = listState, modifier = modifier
                    .padding(top = 45.dp, start = 45.dp, end = 45.dp, bottom = 36.dp)
            )
            {
                items(rules) { item ->
                    Text(
                        text = stringResource(id = item.rule),
                        color = colorResource(id = R.color.white)
                    )
                }
            }
        } else {
            Column {
                Text(
                    text = stringResource(id = hint),
                    color = colorResource(id = R.color.white),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        val showButton by remember {
            derivedStateOf {
                listState.firstVisibleItemIndex > 0
            }
        }

        AnimatedVisibility(visible = showButton) {
            ScrollToTopButton(
                onClick = {
                    coroutineScope.launch {
                        // Animate scroll to the first item
                        listState.animateScrollToItem(index = 0)
                    }
                }
            )
        }
    }
}

/**
 * IndeterminateCircularIndicator UI/UX component for user to see app is loading
 */
@Composable
fun IndeterminateCircularIndicator() {
    var loading by remember { mutableStateOf(true) }

    Timer().schedule(7000) {
        loading = false
    }
    if (!loading) return

    CircularProgressIndicator(
        modifier = Modifier.width(100.dp),
        color = MaterialTheme.colorScheme.onPrimary,
        trackColor = MaterialTheme.colorScheme.inversePrimary,
    )
}
