package com.moondroid.composemap.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.moondroid.composemap.MainViewModel

@Composable
fun KakaoMapScreen() {
    val viewModel = hiltViewModel<MainViewModel>()
    val location by viewModel.location.collectAsStateWithLifecycle()
    val loading by viewModel.loading.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.updateLocation()
    }

    if (loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        MyKakaoMap(location.latitude, location.longitude)
    }
}

@Composable
fun MyKakaoMap(
    lat: Double,
    lng: Double
) {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    AndroidView(modifier = Modifier.fillMaxSize(), factory = { _ ->
        mapView.apply {
            start(object : MapLifeCycleCallback() {
                override fun onMapDestroy() {
                    Log.e("tag", "map destroy")
                }

                override fun onMapError(p0: Exception?) {
                    Log.e("tag", "map error : $p0")
                }
            }, object : KakaoMapReadyCallback() {
                override fun onMapReady(kakaoMap: KakaoMap) {
                    kakaoMap.moveCamera(
                        CameraUpdateFactory.newCenterPosition(LatLng.from(lat, lng))
                    )
                }
            })
        }
    })
}