package com.moondroid.compose.mvi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moondroid.compose.mvi.ui.features.home.HomeViewModel
import com.moondroid.compose.mvi.ui.features.home.composable.HomeListScreen
import com.moondroid.compose.mvi.ui.features.note.composable.NoteScreen

object ArgumentKey {
    const val NOTE_ID = "noteId"
}

@Composable
fun MyNavGraph(
    navController: NavHostController = rememberNavController(),
    navigationAction: MyNavigationAction,
    startDestination: String = MyDestination.HOME_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MyDestination.HOME_ROUTE) { _ ->
            //Main 에서 viewModel 생성 후 전달
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeListScreen(
                navigationAction = navigationAction,
                viewModel = homeViewModel
            )
        }

        composable(route = MyDestination.NOTE_ROUTE) { backStackEntry ->
            //Note 에서 viewModel 생성
            //val noteViewModel = hiltViewModel<NoteViewModel>()

            NoteScreen(
                /*navController = navController,
                //noteViewModel = noteViewModel
                noteId = backStackEntry.arguments?.getString(ArgumentKey.NOTE_ID) ?: ""*/
            )
        }
    }
}