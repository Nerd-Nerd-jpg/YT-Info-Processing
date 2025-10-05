package com.example.view_all_videos_sdk.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_sdk.data.database.models.YTVideoDataDO
import com.example.view_all_videos_sdk.domain.ViewAllRecordsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewAllVideosViewModel
    @Inject
    constructor(
        private val youTubeUseCases: ViewAllRecordsUseCases,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(ViewVidsState())
        val uiState: StateFlow<ViewVidsState> = _uiState.asStateFlow()

        // Initialize by fetching videos
        init {
            viewModelScope.launch {
                loadVideos()
            }
        }

        private fun loadVideos() {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true) }
                youTubeUseCases
                    .getAllVideos()
                    .catch { e ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = e.message ?: "Failed to load videos",
                            )
                        }
                    }.collect { videos ->
                        _uiState.update {
                            it.copy(
                                videosDisplayed = videos,
                                isLoading = false,
                                error = null,
                            )
                        }
                    }
            }
        }

        fun retryLoadingVideos() {
            loadVideos()
        }
    }

data class ViewVidsState(
    val isLoading: Boolean = false,
    val videosDisplayed: List<YTVideoDataDO> = arrayListOf(),
    val error: String? = null,
)

data class YTVideoDisplayObject(
    val id: String,
    val name: String,
    val thumbnailId: Int,
)
