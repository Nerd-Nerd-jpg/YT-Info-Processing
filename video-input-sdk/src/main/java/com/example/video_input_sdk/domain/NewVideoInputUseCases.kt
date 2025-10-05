package com.example.video_input_sdk.domain

import com.example.core_sdk.domain.repodefinitions.DatabaseRepositoryDefinition
import com.example.core_sdk.data.database.models.YTVideoDataDO
import com.example.ytinfoprocessing.data.network.models.YoutubeVideoDataResponse
import javax.inject.Inject

data class InputVerificationUseCases(
    val getVideoDataUseCase: GetVideoDataUseCase,
    val setVideoAttributeUseCase: SetVideoAttributeUseCase,
    val insertVideo: InsertVideoUseCase,
)

class GetVideoDataUseCase
    @Inject
    constructor(
        private val repository: NetworkRepositoryDefinition,
    ) {
        suspend operator fun invoke(videoId: String): Result<YoutubeVideoDataResponse> = repository.getVideoDetails(videoId)
    }

class InsertVideoUseCase
    @Inject
    constructor(
        private val repository: DatabaseRepositoryDefinition,
    ) {
        suspend operator fun invoke(video: YTVideoDataDO) {
            repository.insertVideo(video)
        }
    }

class SetVideoAttributeUseCase
    @Inject
    constructor(
        private val repository: DatabaseRepositoryDefinition,
    ) {
        suspend operator fun invoke(
            videoId: String,
            attributeName: String,
        ) {
            repository.setVideoAttribute(videoId, attributeName)
        }
    }
