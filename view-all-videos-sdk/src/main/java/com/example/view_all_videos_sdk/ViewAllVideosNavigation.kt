package com.example.view_all_videos_sdk

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.view_all_videos_sdk.presentation.ViewAllRecordsScreen
import com.example.view_all_videos_sdk.presentation.ViewAllVideosViewModel
import com.example.ytinfoprocessing.FeatureNavigationProvider
import com.example.ytinfoprocessing.ViewAllVideosScreens
import javax.inject.Inject

fun NavGraphBuilder.viewAllVideosNavigationGraph(
    navController: NavHostController, // To be used only for navigating within the view_all_videos_sdk module
) {
    composable(ViewAllVideosScreens.ViewAllVideosScreen().route) {
        val viewModel: ViewAllVideosViewModel = hiltViewModel()
        ViewAllRecordsScreen(viewModel)
    }
}

// This is the implementation of the contract
class ViewAllVideosNavigationProvider @Inject constructor() : FeatureNavigationProvider {

    override fun createAttributeManagementGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
    ) {}

    override fun createAddNewVideoGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        attributeAssignmentScreenRoute: String,
    ) {}

    override fun createViewAllVideosGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
    ) {
        navGraphBuilder.viewAllVideosNavigationGraph(navController)
    }
}