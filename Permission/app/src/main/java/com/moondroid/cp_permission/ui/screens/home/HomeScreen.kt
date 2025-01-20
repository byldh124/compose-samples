package com.moondroid.cp_permission.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moondroid.cp_permission.permission.rememberPermissionHelper
import com.moondroid.cp_permission.ui.widget.BaseLayout

@Composable
fun HomeScreen(
    navigateToImageList: () -> Unit,
    navigateToMap: () -> Unit,
    navigateToCamera: () -> Unit,
) {

    val imagePermissionHelper = rememberPermissionHelper {
        if (it) navigateToImageList()
    }

    val locationPermissionHelper = rememberPermissionHelper {
        if (it) navigateToMap()
    }

    val cameraPermissionHelper = rememberPermissionHelper {
        if (it) navigateToCamera()
    }


    BaseLayout(
        "Home"
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button({
                imagePermissionHelper.checkImagePermission()
            }) {
                Text("이미지 불러오기")
            }

            Button({
                locationPermissionHelper.checkLocationPermission()
            }) {
                Text("지도 불러오기")
            }

            Button({
                cameraPermissionHelper.checkCameraPermission()
            }) {
                Text("카메라 불러오기")
            }
        }
    }
}