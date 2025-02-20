package com.moondroid.compose.paging.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moondroid.compose.paging.ui.features.user.composable.UserListScreen

@Composable
fun MyNavGraph(
    navController: NavHostController = rememberNavController(),
    navigationAction: MyNavigationAction,
    startDestination: String = MyDestination.USER_LIST,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MyDestination.USER_LIST) { _ ->
            //Main 에서 viewModel 생성 후 전달
            UserListScreen()
        }
    }
}