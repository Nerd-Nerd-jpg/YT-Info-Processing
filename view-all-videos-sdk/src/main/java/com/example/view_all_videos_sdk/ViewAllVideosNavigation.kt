package com.example.view_all_videos_sdk

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.view_all_videos_sdk.presentation.ViewAllRecordsScreen
import com.example.view_all_videos_sdk.presentation.ViewAllVideosViewModel

fun NavGraphBuilder.viewAllVideosNavigationGraph(
    navController: NavHostController, // To be used only for navigating within the view_all_videos_sdk module
) {
    composable(ViewAllVideosScreens.ViewAllVideosScreen().route) {
        val viewModel: ViewAllVideosViewModel = hiltViewModel()
        ViewAllRecordsScreen(viewModel)
    }
}

sealed class ViewAllVideosScreens {
    data class ViewAllVideosScreen(
        val title: String = "View All Videos",
        val route: String = "viewAllVideosScreen",
    )
}
