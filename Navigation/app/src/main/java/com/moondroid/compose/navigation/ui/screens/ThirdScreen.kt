package com.moondroid.compose.navigation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moondroid.compose.navigation.misc.MyButton
import com.moondroid.compose.navigation.misc.MyScaffold
import com.moondroid.compose.navigation.route.First

@Composable
fun ThirdScreen(navController: NavController) {
    MyScaffold("3번째 화면", onBack = {
        navController.navigateUp()
    }) {
        Box(modifier = Modifier.padding(16.dp)) {
            MyButton("첫번째 화면으로 이동") {
                navController.navigate(First) {
                    popUpTo<First> { inclusive = true }
                }
            }
        }
    }
}