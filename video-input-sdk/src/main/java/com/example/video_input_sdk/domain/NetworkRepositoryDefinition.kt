package com.example.video_input_sdk.domain

import com.example.ytinfoprocessing.data.network.models.YoutubeVideoDataResponse

interface NetworkRepositoryDefinition {
    suspend fun getVideoDetails(videoId: String): Result<YoutubeVideoDataResponse>
}
