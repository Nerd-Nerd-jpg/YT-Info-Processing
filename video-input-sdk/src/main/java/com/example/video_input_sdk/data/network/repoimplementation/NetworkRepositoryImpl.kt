package com.example.ytinfoprocessing.data.network.repoimplementation

import com.example.video_input_sdk.data.network.YoutubeApiServices
import com.example.video_input_sdk.domain.NetworkRepositoryDefinition
import com.example.ytinfoprocessing.data.network.models.YoutubeVideoDataResponse
import javax.inject.Inject

class NetworkRepositoryImpl
    @Inject
    constructor(
        private val apiService: YoutubeApiServices,
    ) : NetworkRepositoryDefinition {
        override suspend fun getVideoDetails(videoId: String): Result<YoutubeVideoDataResponse> =
            try {
                val response = apiService.getVideoDetails(videoId = videoId)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
    }
