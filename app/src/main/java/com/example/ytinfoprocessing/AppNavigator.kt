package com.example.ytinfoprocessing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import java.net.URLEncoder

@Composable
fun AppNavigation(
    sharedUrl: String,
    featureNavigationProviders: Set<FeatureNavigationProvider>,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberNavController()

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { AppModalDrawerSheet(navController) },
    ) {
        // Navigate to URL screen only if a valid URL is shared, after NavHost is set up
        LaunchedEffect(sharedUrl) {
            if (sharedUrl.isNotEmpty() && sharedUrl != "No link shared yet") {
                // Encode the URL to handle special characters
                val encodedUrl = URLEncoder.encode(sharedUrl, "UTF-8")

                navController.navigate(AddNewVideoScreens.InsertNewVideoFromDeepLinkScreen().route + "/$encodedUrl")
            }
        }

        NavHost(navController = navController, startDestination = "${ AttributeManagementScreens.AttributeScreen().route }/true") {
            // Iterate through all injected providers and execute their graph creation
            featureNavigationProviders.forEach { provider ->
                provider.createAttributeManagementGraph(this, navController) // Assuming this is needed on main screen
                provider.createAddNewVideoGraph(
                    this,
                    navController,
                    "${ AttributeManagementScreens.AttributeScreen().route }/false",
                )
                provider.createViewAllVideosGraph(this, navController)
            }
        }
    }
}

@Composable
fun AppModalDrawerSheet(navController: NavHostController) {
    ModalDrawerSheet {
        Column(
            modifier =
                Modifier
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
        ) {
            Spacer(Modifier.height(12.dp))
            Text(
                getCurrentPageTitle(
                    navController
                        .currentBackStackEntryAsState()
                        .value
                        ?.destination
                        ?.route ?: "",
                ),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge,
            )
            HorizontalDivider()

            Text("Pages", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
            NavigationDrawerItem(
                label = { Text(AttributeManagementScreens.AttributeScreen().title) },
                selected = false,
                onClick = {
                    navController.navigate(
                        "${ AttributeManagementScreens.AttributeScreen().route }/true",
                    )
                },
            )

            NavigationDrawerItem(
                label = { Text(ViewAllVideosScreens.ViewAllVideosScreen().title) },
                selected = false,
                onClick = { navController.navigate(ViewAllVideosScreens.ViewAllVideosScreen().route) },
            )

            /* TODO: Nice to haves. Not available right now
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text("Section 2", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
            NavigationDrawerItem(
                label = { Text("Settings") },
                selected = false,
                icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                badge = { Text("20") }, // Placeholder
                onClick = { /* Handle click */ }
            )

            NavigationDrawerItem(
                label = { Text("Help and feedback") },
                selected = false,
                icon = { Icon(Icons.AutoMirrored.Default.ArrowForward, contentDescription = null) },
                onClick = { /* Handle click */ },
            )
            Spacer(Modifier.height(12.dp))
             */
        }
    }
}

fun getCurrentPageTitle(route: String): String =
    when (route) {
        AttributeManagementScreens.AttributeScreen().route -> AttributeManagementScreens.AttributeScreen().title
        AddNewVideoScreens.InsertNewVideoFromDeepLinkScreen().route -> AddNewVideoScreens.InsertNewVideoFromDeepLinkScreen().title
        ViewAllVideosScreens.ViewAllVideosScreen().route -> ViewAllVideosScreens.ViewAllVideosScreen().title
        else -> "Anomaly"
    }

//region routes
sealed class ViewAllVideosScreens {
    data class ViewAllVideosScreen(
        val title: String = "View All Videos",
        val route: String = "viewAllVideosScreen",
    )
}

sealed class AttributeManagementScreens {
    data class AttributeScreen(
        val title: String = "Attribute Catalogue",
        val route: String = "attributeScreen",
    )
}

sealed class AddNewVideoScreens {
    data class InsertNewVideoFromDeepLinkScreen(
        val title: String = "Insert New Video",
        val route: String = "insertNewVideoScreen",
    )
}
//endregion

//region navigation contract
// A sealed interface or class for all feature navigation contracts
interface FeatureNavigationProvider {
    // Defines the contract for the Attribute Management Graph
    fun createAttributeManagementGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
    )

    // Defines the contract for the Add New Video Graph
    fun createAddNewVideoGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        onVideoAddedDestination: String,
    )

    // Defines the contract for the View All Videos Graph
    fun createViewAllVideosGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
    )
    // ... other feature navigation contracts
}
//endregion