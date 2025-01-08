package com.moondroid.compose_camerax.ui.features.splash

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import java.security.Permissions

@Composable
fun SplashScreen(navigateToCamera: () -> Unit) {

    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            navigateToCamera()
        } else {

        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button({
            checkPermission(context, permissionLauncher) {
                navigateToCamera()
            }
        }) {
            Text("to camera")
        }
    }
}

private fun checkPermission(
    context: Context,
    requestPermission: ManagedActivityResultLauncher<String, Boolean>,
    permissionGranted: () -> Unit
) {
    if (ContextCompat.checkSelfPermission(context, CAMERA) == PERMISSION_GRANTED) {
        permissionGranted()
    } else {
        requestPermission.launch(CAMERA)
    }
}