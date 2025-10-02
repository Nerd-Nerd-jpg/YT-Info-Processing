package com.example.video_input_sdk.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertNewVideoScreen(
    viewModel: InputVerificationViewModel,
    encodedUrl: String,
) {
    LaunchedEffect(Unit) {
        val decodedUrl = viewModel.decodeUrl(encodedUrl)
        viewModel.extractVideoData(decodedUrl)
    }

    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Input Verification") },
                actions = {
                    IconButton(onClick = { viewModel.assignAttributes() }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Attributes",
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
            val details = uiState.value.inputVideoDetails

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start,
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = details?.videoThumbnail, // Replace with your image URL
                    contentDescription = "Image from URL",
                    contentScale = ContentScale.FillWidth,
                )

                TwoColumnRowItem(
                    "Video Name",
                    details?.videoName ?: "Input Verification Error",
                )

                TwoColumnRowItem(
                    "Channel Name",
                    details?.channelName ?: "Input Verification Error",
                )

                Text(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    text = "Attributes attached",
                )
            }
        }
    }
}

@Composable
private fun TwoColumnRowItem(
    characteristic: String,
    value: String,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = characteristic,
            fontSize = 16.sp,
            textAlign = TextAlign.Start,
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier = Modifier.weight(3f),
            text = value,
            fontSize = 16.sp,
            textAlign = TextAlign.Start,
        )
    }
}
