package com.example.ytinfoprocessing.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ytinfoprocessing.data.database.models.AttributeDO
import com.example.ytinfoprocessing.data.database.models.VideoAttributeDO
import com.example.ytinfoprocessing.data.database.models.YTVideoDataDO
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertVideo(video: YTVideoDataDO)

    @Query("SELECT * FROM videos WHERE videoId = :videoId")
    suspend fun getVideoById(videoId: String): YTVideoDataDO?

    @Query("SELECT * FROM videos")
    fun getAllVideos(): Flow<List<YTVideoDataDO>>
}

@Dao
interface VideoAttributeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun setVideoAttribute(videoAttribute: VideoAttributeDO)

    @Query("SELECT * FROM video_attributes WHERE videoId = :videoId")
    fun getAllAttributesOfVideo(videoId: String): Flow<List<VideoAttributeDO>>
}

@Dao
interface AttributeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAttribute(videoAttribute: AttributeDO)

    @Query("SELECT * FROM attributes")
    fun getAllAttributes(): Flow<List<AttributeDO>>

    @Delete
    suspend fun deleteAttribute(videoAttribute: AttributeDO)
}
