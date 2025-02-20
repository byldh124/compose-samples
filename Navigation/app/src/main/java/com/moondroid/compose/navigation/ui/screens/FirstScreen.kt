package com.moondroid.compose.navigation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moondroid.compose.navigation.misc.MyButton
import com.moondroid.compose.navigation.misc.MyScaffold
import com.moondroid.compose.navigation.route.Second

@Composable
fun FirstScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    MyScaffold("첫 화면") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
        ) {
            MyButton("두번째 화면으로 이동") {
                navController.navigate(Second)
            }
        }
    }
}