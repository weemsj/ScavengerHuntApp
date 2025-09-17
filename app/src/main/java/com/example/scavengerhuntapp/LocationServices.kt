package com.example.scavengerhuntapp

/**
 *  Assignment - 6: Mobile Treasure Hunt
 *
 *  Jacqueline Weems
 *
 *  Oregon State University -CS 429
 *  weemsj@oregonstate.edu
 *
 *  LocationServices.kt
 *
 */

import android.Manifest
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


/**
 * LocationService that checks coordination using Haversine.kt
 * @param currentLocation
 * @param clueLocation
 * @return [Double]
 */
fun checkCoords(currentLocation: Geo, clueLocation: Geo): Double {
    return currentLocation.haversine(clueLocation)
}

/**
 * Gets current location, location is updated in a callback function
 * @param location
 * @param scope
 * @param locationProviderClient
 * @param isPreciseLocation
 */
@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)
fun getLocation(
    location: (Pair<Double, Double>) -> Unit,
    scope: CoroutineScope,
    locationProviderClient: FusedLocationProviderClient,
    isPreciseLocation: Boolean
) {
    scope.launch(Dispatchers.IO) {
        val priority = if (isPreciseLocation) {
            Priority.PRIORITY_HIGH_ACCURACY
        } else {
            Priority.PRIORITY_BALANCED_POWER_ACCURACY
        }
        val result = locationProviderClient.getCurrentLocation(
            priority,
            CancellationTokenSource().token,
        ).await()
        result?.let { fetchedLocation ->
            location(
                Pair(fetchedLocation.latitude, fetchedLocation.longitude)
            )
        }
    }

}


