package com.example.video_input_sdk.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.video_input_sdk.domain.InputVerificationUseCases
import com.example.ytinfoprocessing.data.database.models.AttributeDO
import com.example.ytinfoprocessing.data.database.models.YTVideoDataDO
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URLDecoder

@HiltViewModel(assistedFactory = InputVerificationViewModel.Factory::class)
class InputVerificationViewModel
    @AssistedInject
    constructor(
        private val inputVerificationUseCases: InputVerificationUseCases,
        @Assisted private val navigateToAttributeScreen: (String) -> Unit,
    ) : ViewModel() {
        @AssistedFactory
        interface Factory {
            fun create(navigateToAttributeScreen: (String) -> Unit): InputVerificationViewModel
        }

        private val _uiState = MutableStateFlow(InputVerificationState())
        val uiState: StateFlow<InputVerificationState> = _uiState.asStateFlow()

        suspend fun extractVideoData(decodedUrl: String) {
            val videoId = extractYouTubeVideoId(decodedUrl)
            _uiState.update {
                it.copy(isLoading = true)
            }

            val result = inputVerificationUseCases.getVideoDataUseCase(videoId ?: "")
            when {
                result.isSuccess -> {
                    result.getOrNull()?.items?.first()?.snippet.let { snippet ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                inputVideoDetails =
                                    InputVideoDetails(
                                        videoUrl = decodedUrl,
                                        videoThumbnail = snippet?.thumbnails?.medium?.url ?: "",
                                        videoName = snippet?.title ?: "",
                                        channelUrl = "",
                                        channelName = snippet?.channelTitle ?: "",
                                    ),
                            )
                        }
                    }
                }

                result.isFailure -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            inputVideoDetails = null,
                            error = result.exceptionOrNull().toString(),
                        )
                    }
                }
            }
        }

        fun insertVideo(video: YTVideoDataDO) {
            viewModelScope.launch {
                try {
                    inputVerificationUseCases.insertVideo(video)
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(error = e.message ?: "Failed to insert video")
                    }
                }
            }
        }

        private fun extractYouTubeVideoId(url: String): String? {
            // Regular expression to match YouTube video IDs in various URL formats
            val regex =
                """(?:youtube(?:-nocookie)?\.com/(?:[^/\n\s]+/\S+/|(?:v|e(?:mbed)?)/|.*[?&]v=)|youtu\.be/)([a-zA-Z0-9_-]{11})""".toRegex()

            // Find the video ID using the regex
            val matchResult = regex.find(url)
            return matchResult?.groupValues?.get(1) // Return the video ID (group 1) or null if not found
        }

        fun decodeUrl(encodedUrl: String): String =
            try {
                URLDecoder.decode(encodedUrl, "UTF-8").ifEmpty { "No URL shared" }
            } catch (e: Exception) {
                "Invalid URL"
            }

        fun assignAttributes() {
            navigateToAttributeScreen(false.toString())
        }
    }

data class InputVerificationState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val inputVideoDetails: InputVideoDetails? = null,
    val availableAttributes: List<AttributeDO>? = null,
)

data class InputVideoDetails(
    val videoThumbnail: String,
    val videoName: String,
    val channelName: String,
    val videoUrl: String,
    val channelUrl: String,
)
