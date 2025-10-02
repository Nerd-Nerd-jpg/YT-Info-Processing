package com.example.ytinfoprocessing.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ytinfoprocessing.data.database.models.AttributeDO
import com.example.ytinfoprocessing.data.database.models.VideoAttributeDO
import com.example.ytinfoprocessing.data.database.models.YTVideoDataDO

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
