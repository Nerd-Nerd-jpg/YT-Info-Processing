package com.example.ytinfoprocessing.concreteImplementations.repoimplementation.di

import android.content.Context
import androidx.room.Room
import com.example.core_sdk.data.database.YouTubeDatabase
import com.example.core_sdk.domain.repodefinitions.DatabaseRepositoryDefinition
import com.example.ytinfoprocessing.concreteImplementations.repoimplementation.impl.repositories.DatabaseRepositoryImpl
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
    // Move the @Provides method into a companion object and mark with @JvmStatic
    companion object {
        @Provides
        @Singleton
        @JvmStatic // This is the key to making the non-abstract function "static"
        fun provideDatabase(
            @ApplicationContext context: Context,
        ): YouTubeDatabase =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    YouTubeDatabase::class.java,
                    "youtube_database",
                ).build()
    }

    @Binds
    abstract fun bindRepo(impl: DatabaseRepositoryImpl): DatabaseRepositoryDefinition
}
