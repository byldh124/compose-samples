package com.moondroid.cp_stepindicator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moondroid.cp_stepindicator.ui.theme.StepIndicatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StepIndicatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        MyApp()
                    }
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val list = listOf("1단계" ,"2단계", "3단계", "4단계", "5단계", "6단계", "7단계", "8단계", "9단계", )
    val indicatorState = rememberIndicatorState(0, list)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StepIndicator(indicatorState)

        Spacer(Modifier.height(40.dp))

        Row {
            Button(onClick = {
                indicatorState.currentPage.value--

            }, enabled = indicatorState.currentPage.value > 0) {
                Text("이전 이동")
            }
            Spacer(Modifier.width(40.dp))

            Button(onClick = {
                indicatorState.currentPage.value++
            }, enabled = indicatorState.currentPage.value < list.lastIndex) {
                Text("다음 이동")
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StepIndicatorTheme {
        Greeting("Android")
    }
}