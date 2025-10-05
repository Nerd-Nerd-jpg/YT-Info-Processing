package com.example.core_sdk.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// region Room Entities
@Entity(tableName = "videos")
data class YTVideoDataDO(
    @PrimaryKey
    val videoId: String,
    @ColumnInfo(name = "thumbnail_id")
    val thumbnailUrl: String,
    val name: String,
    val description: String,
    @ColumnInfo(name = "channel_id")
    val channelId: String,
)

@Entity(tableName = "video_attributes", primaryKeys = ["videoId", "attribute"])
data class VideoAttributeDO(
    val videoId: String,
    val attribute: String,
)

@Entity(tableName = "attributes")
data class AttributeDO(
    @PrimaryKey
    val attribute: String,
)
//endregion
