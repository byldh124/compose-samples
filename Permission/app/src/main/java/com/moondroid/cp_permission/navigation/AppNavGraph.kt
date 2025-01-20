package com.moondroid.cp_permission.navigation

import CameraScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moondroid.cp_permission.ui.screens.home.HomeScreen
import com.moondroid.cp_permission.ui.screens.image.ImageListScreen
import com.moondroid.cp_permission.ui.screens.map.MapScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    NavHost(navController, Home) {
        composable<Home> {
            HomeScreen(
                navigateToImageList = {
                    navController.navigate(ImageList)
                },
                navigateToMap = {
                    navController.navigate(Map)
                },
                navigateToCamera = {
                    navController.navigate(Camera)
                }
            )
        }

        composable<ImageList> {
            ImageListScreen {
                navController.popBackStack()
            }
        }

        composable<Map> {
            MapScreen {
                navController.popBackStack()
            }
        }

        composable<Camera> {
            CameraScreen {
                navController.popBackStack()
            }
        }
    }
}