package com.example.core_sdk.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core_sdk.data.database.models.AttributeDO
import com.example.core_sdk.data.database.models.VideoAttributeDO
import com.example.core_sdk.data.database.models.YTVideoDataDO
import com.example.ytinfoprocessing.data.database.AttributeDao
import com.example.ytinfoprocessing.data.database.VideoAttributeDao
import com.example.ytinfoprocessing.data.database.VideoDao

// Database
@Database(
    entities = [
        YTVideoDataDO::class,
        VideoAttributeDO::class,
        AttributeDO::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class YouTubeDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao

    abstract fun videoAttributeDao(): VideoAttributeDao

    abstract fun attributeDao(): AttributeDao
}
