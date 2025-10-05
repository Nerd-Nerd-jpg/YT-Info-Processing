package com.example.view_all_videos_sdk.di

import com.example.core_sdk.domain.repodefinitions.DatabaseRepositoryDefinition
import com.example.view_all_videos_sdk.ViewAllVideosNavigationProvider
import com.example.view_all_videos_sdk.domain.GetAllVideosUseCase
import com.example.view_all_videos_sdk.domain.GetVideoAttributesUseCase
import com.example.view_all_videos_sdk.domain.ViewAllRecordsUseCases
import com.example.ytinfoprocessing.FeatureNavigationProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewAllVideosScope

@Module
@InstallIn(SingletonComponent::class)
object ViewAllVideosModule {
//    @ViewAllVideosScope
    @Provides
    fun provideViewAllRecordsUseCases(dataBaseRepo: DatabaseRepositoryDefinition): ViewAllRecordsUseCases =
        ViewAllRecordsUseCases(
            getAllVideos = GetAllVideosUseCase(dataBaseRepo),
//            setNewAttributeUseCase = SetNewAttributeUseCase(dataBaseRepo),
//            getAllAttributesUseCase = GetAllAttributesUseCase(dataBaseRepo),
            getVideoAttribute = GetVideoAttributesUseCase(dataBaseRepo),
        )
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ViewAllNavigationModule {

    @Binds
    @IntoSet
    abstract fun bindViewAllVideosNavigationProvider(
        provider: ViewAllVideosNavigationProvider,
    ): FeatureNavigationProvider
}