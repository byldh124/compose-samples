package com.moondroid.compose.paging.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.moondroid.compose.paging.common.navigation.MyDestination
import com.moondroid.compose.paging.common.navigation.MyNavigationAction
import com.moondroid.compose.paging.common.navigation.MyNavGraph
import com.moondroid.compose.paging.ui.theme.PagingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    PagingTheme {
        val navController = rememberNavController()
        val navigationAction = remember(navController) {
            MyNavigationAction(navController = navController)
        }
        MyNavGraph(
            navController = navController,
            navigationAction = navigationAction,
            startDestination = MyDestination.SPLASH_ROUTE
        )
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
    PagingTheme {
        Greeting("Android")
    }
}