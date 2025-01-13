package com.moondroid.composemap.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moondroid.composemap.MainViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun NaverMapScreen() {
    val viewModel = hiltViewModel<MainViewModel>()
    val location by viewModel.location.collectAsStateWithLifecycle()

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(viewModel) {
        viewModel.updateLocation()
    }

    NaverMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState.apply {
            position = CameraPosition(LatLng(location.latitude, location.longitude), 15.0)
        },
    ) {

    }
}