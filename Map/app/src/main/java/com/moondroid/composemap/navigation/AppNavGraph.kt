package com.moondroid.composemap.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moondroid.composemap.ui.screens.GoogleMapScreen
import com.moondroid.composemap.ui.screens.KakaoMapScreen
import com.moondroid.composemap.ui.screens.NaverMapScreen
import com.moondroid.composemap.ui.screens.SplashScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Splash) {
        composable<Splash> {
            SplashScreen(
                navigateToGoogleMap = {
                    navController.navigate(GoogleMap)
                }, navigateToNaverMap = {
                    navController.navigate(NaverMap)
                }, navigateToKakaoMap = {
                    navController.navigate(KakaoMap)
                }
            )
        }

        composable<GoogleMap> {
            GoogleMapScreen()
        }

        composable<NaverMap> {
            NaverMapScreen()
        }

        composable<KakaoMap> {
            KakaoMapScreen()
        }
    }
}
