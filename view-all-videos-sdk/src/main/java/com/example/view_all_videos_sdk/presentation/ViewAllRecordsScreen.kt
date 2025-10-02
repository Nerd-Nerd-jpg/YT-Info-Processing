package com.example.view_all_videos_sdk.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.view_all_videos_sdk.R
import com.example.ytinfoprocessing.data.database.models.YTVideoDataDO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewAllRecordsScreen(viewModel: ViewAllVideosViewModel) {
    val uiState = viewModel.uiState.collectAsState().value
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Handle upload action here */ },
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(16.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Upload",
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("View all videos") },
                actions = {
                    IconButton(onClick = { /* Handle filter action */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Filter",
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
            )
        },
    ) { innerPadding ->
        Surface(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Coming Soon",
                    color = Color.Blue,
                    fontSize = 55.sp,
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.Bold,
                )
            }
//            VideoGridDisplay(uiState)
        }
    }
}

@Composable
private fun VideoGridDisplay(uiState: ViewVidsState) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(uiState.videosDisplayed) { item ->
            GridItem(item)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PreviewViewAllVideosFullScreen() {
    val dummyState =
        ViewVidsState(
            isLoading = false,
            videosDisplayed =
                arrayListOf(
                    YTVideoDataDO(
                        videoId = "Id",
                        channelId = "ChannelId",
                        thumbnailUrl = "https://yt3.ggpht.com/2eI1TjX447QZFDe6R32K0V2mjbVMKT5mIfQR-wK5bAsxttS_7qzUDS1ojoSKeSP0NuWd6sl7qQ=s240-c-k-c0x00ffffff-no-rj",
                        name = "name",
                        description = "description",
                    ),
                ),
            error = null,
        )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Handle upload action here */ },
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(16.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Upload",
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("View all videos") },
                actions = {
                    IconButton(onClick = { /* Handle filter action */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Filter",
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
            )
        },
    ) { innerPadding ->
        Surface(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            color = MaterialTheme.colorScheme.background,
        ) {
            VideoGridDisplay(dummyState)
        }
    }
}

@Composable
fun GridItem(videoElement: YTVideoDataDO) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .aspectRatio(9f / 16f) // Updated to 9:16 for YouTube Shorts
                .padding(4.dp),
    ) {
        // Load the thumbnail image
        AsyncImage(
            model =
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(videoElement.thumbnailUrl)
                    .crossfade(true)
                    .build(),
            contentDescription = "Sample Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.imgnotfound), // Add placeholder image
            error = painterResource(R.drawable.image_error), // Add error image
        )

        // Black gradient at the bottom, fading to transparent upwards
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        brush =
                            Brush.verticalGradient(
                                colors =
                                    listOf(
                                        Color.Black.copy(alpha = 0.7f),
                                        Color.Transparent,
                                    ),
                                startY = 0f,
                                endY = 100f,
                            ),
                    ).padding(8.dp),
        ) {
            // Video name in white at bottom-left
            Text(
                text = videoElement.name,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.BottomStart),
            )
        }
    }
}
