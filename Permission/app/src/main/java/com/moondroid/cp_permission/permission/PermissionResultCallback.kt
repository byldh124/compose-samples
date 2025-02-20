package com.moondroid.cp_permission.permission

interface PermissionResultCallback {
     fun onPermissionGranted()
     fun onPermissionDenied()
}