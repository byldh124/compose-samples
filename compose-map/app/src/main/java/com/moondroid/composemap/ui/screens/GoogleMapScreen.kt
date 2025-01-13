package com.moondroid.composemap.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState
import com.moondroid.composemap.MainViewModel

@Composable
fun GoogleMapScreen() {
    val viewModel = hiltViewModel<MainViewModel>()
    val location by viewModel.location.collectAsStateWithLifecycle()

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(viewModel) {
        viewModel.updateLocation()
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState.apply {
            position = CameraPosition(location, 15f, 0f, 0f)
        },
        properties = MapProperties(
            isMyLocationEnabled = true
        ),
    )
}