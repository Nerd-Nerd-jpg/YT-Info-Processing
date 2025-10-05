package com.example.view_all_videos_sdk.domain

import com.example.core_sdk.data.database.models.VideoAttributeDO
import com.example.core_sdk.data.database.models.YTVideoDataDO
import com.example.core_sdk.domain.repodefinitions.DatabaseRepositoryDefinition
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVideoAttributesUseCase
    @Inject
    constructor(
        private val repository: DatabaseRepositoryDefinition,
    ) {
        suspend operator fun invoke(videoId: String): Flow<List<VideoAttributeDO>> = repository.getVideoAttributes(videoId)
    }

class GetVideoByIdUseCase
    @Inject
    constructor(
        private val repository: DatabaseRepositoryDefinition,
    ) {
        suspend operator fun invoke(videoId: String): YTVideoDataDO? = repository.getVideoById(videoId)
    }

class GetAllVideosUseCase
    @Inject
    constructor(
        private val repository: DatabaseRepositoryDefinition,
    ) {
        operator fun invoke(): Flow<List<YTVideoDataDO>> = repository.getAllVideos()
    }
