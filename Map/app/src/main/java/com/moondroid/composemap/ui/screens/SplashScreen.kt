package com.moondroid.composemap.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import java.security.Permissions

@Composable
fun SplashScreen(
    navigateToGoogleMap: () -> Unit,
    navigateToNaverMap: () -> Unit,
    navigateToKakaoMap: () -> Unit,
) {
    val context = LocalContext.current

    val mapType = remember { mutableStateOf(MapType.GOOGLE) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        val granted = it.all { permission -> permission.value }
        if (granted) {
            when (mapType.value) {
                MapType.GOOGLE -> navigateToGoogleMap()
                MapType.NAVER -> navigateToNaverMap()
                MapType.KAKAO -> navigateToKakaoMap()
            }
        } else {

        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button({
            mapType.value = MapType.GOOGLE
            checkPermission(context, launcher, navigateToGoogleMap)
        }) { Text("Google Map") }
        Button({
            mapType.value = MapType.NAVER
            checkPermission(context, launcher, navigateToNaverMap)
        }) { Text("Naver Map") }
        Button({
            mapType.value = MapType.KAKAO
            checkPermission(context, launcher, navigateToKakaoMap)
        }) { Text("Kakao Map") }
    }
}


private enum class MapType() {
    GOOGLE, NAVER, KAKAO
}

private fun checkPermission(
    context: Context,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
    onGranted: () -> Unit
) {
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        onGranted()
    } else {
        launcher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
    }
}