package com.example.scavengerhuntapp

/**
 *  Assignment - 6: Mobile Treasure Hunt
 *
 *  Jacqueline Weems
 *
 *  Oregon State University -CS 429
 *  weemsj@oregonstate.edu
 *
 *  Haversine.kt
 *
 */

import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt


class Geo(private val lat: Double, private val lon: Double) {

    companion object {
        const val earthRadiusKm: Double = 6372.8
    }

    /**
     * Haversine formula. Giving great-circle distances between two points on a sphere from their longitudes and latitudes.
     * It is a special case of a more general formula in spherical trigonometry, the law of haversines, relating the
     * sides and angles of spherical "triangles".
     *
     * https://rosettacode.org/wiki/Haversine_formula#Java
     *
     * @return Distance in kilometers
     */
    fun haversine(destination: Geo): Double {
        val dLat = Math.toRadians(destination.lat - this.lat)
        val dLon = Math.toRadians(destination.lon - this.lon)
        val originLat = Math.toRadians(this.lat)
        val destinationLat = Math.toRadians(destination.lat)

        val a = sin(dLat / 2).pow(2.toDouble()) + sin(dLon / 2).pow(2.toDouble()) * cos(originLat) * cos(destinationLat)
        val c = 2 * asin(sqrt(a))
        return earthRadiusKm * c
    }

}
