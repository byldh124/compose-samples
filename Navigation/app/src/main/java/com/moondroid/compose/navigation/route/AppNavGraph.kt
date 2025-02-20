package com.moondroid.compose.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.moondroid.compose.navigation.ui.screens.FirstScreen
import com.moondroid.compose.navigation.ui.screens.NewNavFirstScreen
import com.moondroid.compose.navigation.ui.screens.NewNavSecondScreen
import com.moondroid.compose.navigation.ui.screens.SecondScreen
import com.moondroid.compose.navigation.ui.screens.ThirdScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = First) {
        composable<First> {
            FirstScreen(navController)
        }

        composable<Second> {
            SecondScreen(navController)
        }

        composable<Third> {
            ThirdScreen(navController)
        }

        //navigation 키워드를 통해 별도의 네이게이션을 구성할 수 있다.
        navigation<NewNav>(startDestination = NewNavFirst) {
            composable<NewNavFirst> {
                NewNavFirstScreen()
            }

            composable<NewNavSecond> {
                NewNavSecondScreen()
            }
        }
    }
}