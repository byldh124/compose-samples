package com.moondroid.composemap

import android.app.Application
import com.naver.maps.map.NaverMapSdk

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // 네이버 맵 클라이언트 초기화
        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient(getString(R.string.naver_client_id))
    }
}