package com.moondroid.composemap.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import java.lang.Exception

@Composable
fun KakaoMapScreen() {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }

    AndroidView(modifier = Modifier.fillMaxSize(), factory = { context ->
        mapView.apply {
            start(object : MapLifeCycleCallback() {
                override fun onMapDestroy() {
                    Log.e("tag", "map destroy")
                }

                override fun onMapError(p0: Exception?) {
                    Log.e("tag", "map error : $p0")
                }
            }, object : KakaoMapReadyCallback() {
                override fun onMapReady(p0: KakaoMap) {
                    Log.e("tag", "map ready")
                }
            })
        }
    })
}