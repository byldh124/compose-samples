package com.moondroid.cp_permission.permission

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionHelper(
    private val context: Context,
    private val requestPermissions: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
    private val onPermissionResult: (isGranted: Boolean) -> Unit,
) {
    fun checkCustomPermission(list: Array<String>) {
        val deniedList =
            list.filter {
                ActivityCompat.checkSelfPermission(
                    context,
                    it
                ) == PackageManager.PERMISSION_DENIED
            }

        if (deniedList.isNotEmpty()) {
            requestPermissions.launch(deniedList.toTypedArray())
        } else {
            onPermissionResult(true)
        }
    }

    fun checkImagePermission() {
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE &&
            (ContextCompat.checkSelfPermission(context, READ_MEDIA_IMAGES) == PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(context, READ_MEDIA_VISUAL_USER_SELECTED) == PERMISSION_GRANTED)
        ) {
            onPermissionResult(true)
        } else if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(context, READ_MEDIA_IMAGES) == PERMISSION_GRANTED
        ) {
            onPermissionResult(true)
        } else if (ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED) {
            onPermissionResult(true)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                requestPermissions.launch(arrayOf(READ_MEDIA_IMAGES, READ_MEDIA_VISUAL_USER_SELECTED))
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissions.launch(arrayOf(READ_MEDIA_IMAGES))
            } else {
                requestPermissions.launch(arrayOf(READ_EXTERNAL_STORAGE))
            }
        }
    }

    fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                context,
                ACCESS_FINE_LOCATION
            ) == PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PERMISSION_GRANTED
        ) {
            onPermissionResult(true)
        } else {
            requestPermissions.launch(arrayOf(ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
        }
    }

    fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(context, CAMERA) == PERMISSION_GRANTED) {
            onPermissionResult(true)
        } else {
            requestPermissions.launch(arrayOf(CAMERA))
        }
    }
}

@Composable
fun rememberPermissionHelper(
    isAllGranted: Boolean = false,
    permissionResult: (isGranted: Boolean) -> Unit
): PermissionHelper {
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val isGranted = if (isAllGranted) {
                permissions.all { it.value }
            } else {
                permissions.any { it.value }
            }
            permissionResult(isGranted)
        }
    return PermissionHelper(context, launcher, permissionResult)
}