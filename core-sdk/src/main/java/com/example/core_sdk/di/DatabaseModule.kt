package com.example.core_sdk.di

import android.content.Context
import androidx.room.Room
import com.example.core_sdk.domain.repodefinitions.DatabaseRepositoryDefinition
import com.example.ytinfoprocessing.data.database.AttributeDao
import com.example.ytinfoprocessing.data.database.VideoAttributeDao
import com.example.ytinfoprocessing.data.database.VideoDao
import com.example.ytinfoprocessing.data.database.YouTubeDatabase
import com.example.ytinfoprocessing.data.database.repoimplementation.DatabaseRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): YouTubeDatabase =
        Room
            .databaseBuilder(
                context.applicationContext,
                YouTubeDatabase::class.java,
                "youtube_database",
            ).build()

    //region Dao-s
    @Provides
    @Singleton
    fun provideVideoDao(database: YouTubeDatabase): VideoDao = database.videoDao()

    @Provides
    @Singleton
    fun provideVideoAttributeDao(database: YouTubeDatabase): VideoAttributeDao = database.videoAttributeDao()

    @Provides
    @Singleton
    fun provideAttributeDao(database: YouTubeDatabase): AttributeDao = database.attributeDao()
    //endregion

    @Provides
    @Singleton
    fun provideDataBaseRepository(
        videoDao: VideoDao,
        videoAttributeDao: VideoAttributeDao,
        attributeDao: AttributeDao,
    ): DatabaseRepositoryDefinition = DatabaseRepositoryImpl(videoDao, videoAttributeDao, attributeDao)
}
