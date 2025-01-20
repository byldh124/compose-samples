package com.moondroid.cp_permission.ui.screens.map

import android.annotation.SuppressLint
import android.os.Looper
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val locationProviderClient: FusedLocationProviderClient
) : ViewModel() {
    //기본값 서울의 위도 경도
    private val _location = MutableStateFlow(LatLng(37.5642135, 127.0016985))
    val location get() = _location.asStateFlow()

    @SuppressLint("MissingPermission")
    fun updateLocation() {
        val request = LocationRequest.Builder(0)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                p0.locations.lastOrNull()?.let {
                    _location.value = LatLng(it.latitude, it.longitude)

                    //최초 1회 이후 callback 삭제
                    locationProviderClient.removeLocationUpdates(this)
                }
            }
        }

        locationProviderClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())
    }
}