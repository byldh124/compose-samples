package com.moondroid.cp_permission.ui.screens.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moondroid.cp_permission.ui.widget.BaseLayout
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreen(navigateUp: () -> Unit) {
    val viewModel = hiltViewModel<MapViewModel>()
    val location by viewModel.location.collectAsStateWithLifecycle()

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(viewModel) {
        viewModel.updateLocation()
    }

    BaseLayout("MAP", navigateUp) {
        NaverMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState.apply {
                position = CameraPosition(LatLng(location.latitude, location.longitude), 15.0)
            },
        )
    }
}