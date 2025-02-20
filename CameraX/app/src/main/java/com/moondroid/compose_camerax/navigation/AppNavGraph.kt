package com.moondroid.compose_camerax.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moondroid.compose_camerax.ui.features.camera.CameraScreen
import com.moondroid.compose_camerax.ui.features.splash.SplashScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Splash) {
        composable<Splash> {
            SplashScreen {
                navController.navigate(Camera)
            }
        }

        composable<Camera> {
            CameraScreen {
                navController.navigateUp()
            }
        }
    }
}