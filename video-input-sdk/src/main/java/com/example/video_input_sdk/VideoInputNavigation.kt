package com.example.video_input_sdk

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.video_input_sdk.presentation.InputVerificationViewModel
import com.example.video_input_sdk.presentation.InsertNewVideoScreen
import com.example.ytinfoprocessing.AddNewVideoScreens
import com.example.ytinfoprocessing.FeatureNavigationProvider
import javax.inject.Inject

fun NavGraphBuilder.addNewVideoNavigationGraph(
    navController: NavHostController, // To be used only for navigating within the video_input_sdk module
    attributeAssignmentScreenRoute: String,
) {
    composable("${AddNewVideoScreens.InsertNewVideoFromDeepLinkScreen().route}/{url}") { backStackEntry ->
        val url = backStackEntry.arguments?.getString("url") ?: ""
        val viewModel: InputVerificationViewModel =
            hiltViewModel(
                creationCallback = { factory: InputVerificationViewModel.Factory ->
                    factory.create({
                        navController.navigate(
                            attributeAssignmentScreenRoute,
                        )
                    })
                },
            )
        InsertNewVideoScreen(viewModel, url)
    }
}

// This is the implementation of the contract
class AddNewVideoNavigationProvider @Inject constructor() : FeatureNavigationProvider {

    override fun createAttributeManagementGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
    ) {}

    override fun createAddNewVideoGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        attributeAssignmentScreenRoute: String,
    ) {
        navGraphBuilder.addNewVideoNavigationGraph(navController, attributeAssignmentScreenRoute)
    }

    override fun createViewAllVideosGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
    ) {}
}