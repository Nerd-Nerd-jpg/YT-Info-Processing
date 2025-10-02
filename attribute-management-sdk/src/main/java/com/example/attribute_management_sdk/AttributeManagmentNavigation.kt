package com.example.attribute_management_sdk

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.attribute_management_sdk.presentation.attributescreen.AttributeScreen
import com.example.attribute_management_sdk.presentation.attributescreen.AttributeViewModel

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

sealed class AttributeManagementScreens {
    data class AttributeScreen(
        val title: String = "Attribute Catalogue",
        val route: String = "attributeScreen",
    )
}
