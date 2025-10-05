package com.example.ytinfoprocessing.concreteImplementations.repoimplementation.di

import android.content.Context
import androidx.room.Room
import com.example.core_sdk.domain.repodefinitions.DatabaseRepositoryDefinition
import com.example.ytinfoprocessing.data.database.AttributeDao
import com.example.ytinfoprocessing.data.database.VideoAttributeDao
import com.example.ytinfoprocessing.data.database.VideoDao
import com.example.core_sdk.data.database.YouTubeDatabase
import com.example.ytinfoprocessing.concreteImplementations.repoimplementation.impl.DatabaseRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {
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

    @Binds
    abstract fun bindRepo(impl: DatabaseRepositoryImpl): DatabaseRepositoryDefinition
}
