package com.example.ytinfoprocessing

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.core_sdk.presentation.ui.theme.YTInfoProcessingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Handle incoming share intent
        val sharedText =
            when {
                intent?.action == Intent.ACTION_SEND && intent.type == "text/plain" -> {
                    intent.getStringExtra(Intent.EXTRA_TEXT) ?: "No link shared yet"
                }
                else -> "No link shared yet"
            }

        setContent {
            YTInfoProcessingTheme {
                AppNavigation(sharedText)
            }
        }
    }
}
