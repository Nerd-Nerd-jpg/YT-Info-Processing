package com.example.ytinfoprocessing.data.database.repoimplementation

import com.example.core_sdk.domain.repodefinitions.DatabaseRepositoryDefinition
import com.example.ytinfoprocessing.data.database.AttributeDao
import com.example.ytinfoprocessing.data.database.VideoAttributeDao
import com.example.ytinfoprocessing.data.database.VideoDao
import com.example.ytinfoprocessing.data.database.models.AttributeDO
import com.example.ytinfoprocessing.data.database.models.VideoAttributeDO
import com.example.ytinfoprocessing.data.database.models.YTVideoDataDO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseRepositoryImpl
    @Inject
    constructor(
        private val videoDao: VideoDao,
        private val videoAttributeDao: VideoAttributeDao,
        private val attributeDao: AttributeDao,
    ) : DatabaseRepositoryDefinition {
        //region videos
        override suspend fun insertVideo(video: YTVideoDataDO) {
            videoDao.insertVideo(video)
        }

        override suspend fun getVideoById(videoId: String): YTVideoDataDO? = videoDao.getVideoById(videoId)

        override fun getAllVideos(): Flow<List<YTVideoDataDO>> = videoDao.getAllVideos()
        //endregion

        //region video attributes
        override suspend fun setVideoAttribute(
            videoId: String,
            attributeName: String,
        ) {
            videoAttributeDao.setVideoAttribute(
                VideoAttributeDO(
                    videoId = videoId,
                    attribute = attributeName,
                ),
            )
        }

        override suspend fun getVideoAttributes(videoId: String): Flow<List<VideoAttributeDO>> =
            videoAttributeDao.getAllAttributesOfVideo(videoId)
        //endregion

        //region attributes
        override suspend fun insertNewAttribute(attributeName: String) {
            attributeDao.insertAttribute(AttributeDO(attributeName))
        }

        override suspend fun getAllAttributes(): Flow<List<AttributeDO>> = attributeDao.getAllAttributes()

        override suspend fun deleteAttribute(attributeName: String) {
            attributeDao.deleteAttribute(AttributeDO(attributeName))
        }
        //endregion
    }
