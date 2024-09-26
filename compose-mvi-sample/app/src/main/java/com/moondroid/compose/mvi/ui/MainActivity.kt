package com.moondroid.compose.mvi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.moondroid.compose.mvi.ui.navigation.MyDestination
import com.moondroid.compose.mvi.ui.navigation.MyNavGraph
import com.moondroid.compose.mvi.ui.navigation.MyNavigationAction
import com.moondroid.compose.mvi.ui.theme.MviTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MviTheme {
                val navController = rememberNavController()
                val navigationAction = remember(navController) {
                    MyNavigationAction(navController = navController)
                }
                MyNavGraph(
                    navController = navController,
                    navigationAction = navigationAction,
                    startDestination = MyDestination.HOME_ROUTE
                )
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
    MviTheme {
        Greeting("Android")
    }
}