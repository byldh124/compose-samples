package com.moondroid.compose.mvi.ui.navigation

import androidx.navigation.NavHostController


object MyDestination {
    const val HOME_ROUTE = "home"
    const val NOTE_ROUTE = "note/{noteId}"
}

class MyNavigationAction(navController: NavHostController) {
    val toNote: (Int) -> Unit = { id ->
        navController.navigate("note/$id")
    }
}