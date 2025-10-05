package com.example.attribute_management_sdk

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.attribute_management_sdk.presentation.attributescreen.AttributeScreen
import com.example.attribute_management_sdk.presentation.attributescreen.AttributeViewModel
import com.example.ytinfoprocessing.AttributeManagementScreens
import com.example.ytinfoprocessing.FeatureNavigationProvider
import javax.inject.Inject

fun NavGraphBuilder.attributeManagementGraph(
    navController: NavHostController, // To be used only for navigating within the attribute_management_sdk module
) {
    composable("${ AttributeManagementScreens.AttributeScreen().route }/{managementMode}") { backStackEntry ->
        val managementMode =
            backStackEntry
                .arguments
                ?.getString("managementMode")
                .toBoolean()

        val viewModel: AttributeViewModel =
            hiltViewModel(
                creationCallback = { factory: AttributeViewModel.Factory ->
                    factory.create(managementMode)
                },
            )
        AttributeScreen(viewModel)
    }
}

// This is the implementation of the contract
class AttributeManagementNavigationProvider @Inject constructor() : FeatureNavigationProvider {

    override fun createAttributeManagementGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
    ) {
        // Use the extension function logic defined in this feature module
        navGraphBuilder.attributeManagementGraph(navController)
    }

    override fun createAddNewVideoGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        onVideoAddedDestination: String,
    ) {}

    override fun createViewAllVideosGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
    ) {}
}