package com.moondroid.compose.navigation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moondroid.compose.navigation.misc.MyButton
import com.moondroid.compose.navigation.misc.MyScaffold
import com.moondroid.compose.navigation.route.Second
import com.moondroid.compose.navigation.route.Third

@Composable
fun SecondScreen(navController: NavController) {
    MyScaffold("두번째 화면", onBack = {
        navController.navigateUp()
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            MyButton("3번째 화면으로 이동 스택 o") {
                navController.navigate(Third)
            }

            MyButton("3번째 화면으로 이동 (스택 x)") {
                navController.navigate(Third) {
                    //popUpTo<First> { inclusive = false }
                    popUpTo<Second> { inclusive = true }
                }
            }
        }
    }
}