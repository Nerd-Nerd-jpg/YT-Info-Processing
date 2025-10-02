package com.example.core_sdk.domain.repodefinitions

import com.example.ytinfoprocessing.data.database.models.AttributeDO
import com.example.ytinfoprocessing.data.database.models.VideoAttributeDO
import com.example.ytinfoprocessing.data.database.models.YTVideoDataDO
import kotlinx.coroutines.flow.Flow

// Repository Interface
interface DatabaseRepositoryDefinition {
    //region videos
    suspend fun insertVideo(VideoDataDefinition: YTVideoDataDO)

    suspend fun getVideoById(videoId: String): YTVideoDataDO?

    fun getAllVideos(): Flow<List<YTVideoDataDO>>
    //endregion

    //region video attributes
    suspend fun setVideoAttribute(
        videoId: String,
        attributeName: String,
    )

    suspend fun getVideoAttributes(videoId: String): Flow<List<VideoAttributeDO>>
    //endregion

    //region attributes
    suspend fun insertNewAttribute(attributeName: String)

    suspend fun getAllAttributes(): Flow<List<AttributeDO>>

    suspend fun deleteAttribute(attributeName: String)
    //endregion
}
