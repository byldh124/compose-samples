package com.moondroid.compose.paging.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.moondroid.compose.paging.ui.navigation.MyDestination
import com.moondroid.compose.paging.ui.navigation.MyNavGraph
import com.moondroid.compose.paging.ui.navigation.MyNavigationAction
import com.moondroid.compose.paging.ui.theme.PagingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(
                topBar = { TopAppBar(title = { Text(text = "github users api") }) },
                modifier = Modifier.fillMaxSize(),
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    MyApp()
                }
            }
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
            startDestination = MyDestination.USER_LIST
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