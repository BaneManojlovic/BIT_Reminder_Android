package com.example.bitreminder.Helpers

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.widget.Toast
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import io.ktor.util.debug.useContextElementInDebugMode

@SuppressLint("MissingPermission")
fun getCurrentLocation(context: Context, onLocationFetched: (location: LatLng) -> Unit) {
    var loc: LatLng
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
        if (location != null) {
            val latitude = location.latitude
            val longitude = location.longitude
            loc = LatLng(latitude, longitude)
            onLocationFetched(loc)
        }
    }
        .addOnFailureListener { exception: Exception ->
            Toast.makeText(context, "Location failure", Toast.LENGTH_SHORT).show()
        }

}