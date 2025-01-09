package com.moondroid.composemap.ui.screens

import android.annotation.SuppressLint
import android.os.Looper
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
@Composable
fun GoogleMapScreen() {
    val context = LocalContext.current
    val latLng = remember { mutableStateOf(LatLng(0.0, 0.0)) }
    val cameraPositionState = rememberCameraPositionState()

    val scope = rememberCoroutineScope()

    val request = LocationRequest.Builder(0)
        .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
        .build()

    val locationProvider = LocationServices.getFusedLocationProviderClient(context)

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            p0.locations.lastOrNull()?.let {
                scope.launch {
                    cameraPositionState.centerOnLocation(LatLng(it.latitude, it.longitude))
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        locationProvider.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())
    }

    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                myLocationButtonEnabled = true
            )
        )
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isMyLocationEnabled = true
        ),
    )
}

private suspend fun CameraPositionState.centerOnLocation(
    location: LatLng
) = animate(
    update = CameraUpdateFactory.newLatLngZoom(
        location,
        15f
    ),
    durationMs = 1000
)