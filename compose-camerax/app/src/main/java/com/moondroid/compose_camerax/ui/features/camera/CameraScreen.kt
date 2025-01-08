package com.moondroid.compose_camerax.ui.features.camera

import android.util.Log
import androidx.camera.compose.CameraXViewfinder
import androidx.camera.core.SurfaceRequest
import androidx.camera.viewfinder.core.ImplementationMode
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CameraScreen(navigateUp: () -> Unit) {
    val viewModel = hiltViewModel<CameraViewModel>()
    val currentSurfaceRequest: SurfaceRequest? by viewModel.surfaceRequests.collectAsStateWithLifecycle()
    val lifeCycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(lifeCycleOwner) {
        viewModel.bindToCamera(context.applicationContext, lifeCycleOwner)
    }

    currentSurfaceRequest?.let { surfaceRequest ->
        CameraXViewfinder(
            surfaceRequest = surfaceRequest,
            implementationMode = ImplementationMode.EXTERNAL, // Or EMBEDDED
            modifier = Modifier.fillMaxSize()
        )
    } ?: run {
        Log.e("TAG", "currentSurface null")
    }
}