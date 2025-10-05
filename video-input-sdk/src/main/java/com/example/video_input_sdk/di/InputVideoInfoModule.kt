package com.example.video_input_sdk.di

import com.example.core_sdk.domain.repodefinitions.DatabaseRepositoryDefinition
import com.example.video_input_sdk.AddNewVideoNavigationProvider
import com.example.video_input_sdk.data.network.YoutubeApiServices
import com.example.video_input_sdk.domain.GetVideoDataUseCase
import com.example.video_input_sdk.domain.InputVerificationUseCases
import com.example.video_input_sdk.domain.InsertVideoUseCase
import com.example.video_input_sdk.domain.NetworkRepositoryDefinition
import com.example.video_input_sdk.domain.SetVideoAttributeUseCase
import com.example.ytinfoprocessing.FeatureNavigationProvider
import com.example.ytinfoprocessing.data.network.repoimplementation.NetworkRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import retrofit2.Retrofit
import javax.inject.Scope
import javax.inject.Singleton

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class InputVideoInfoScope

@Module
@InstallIn(SingletonComponent::class)
object InputVideoInfoModule {
//    @InputVideoInfoScope
    @Provides
    fun provideInputVerificationUseCases(
        networkRepo: NetworkRepositoryDefinition,
        dataBaseRepo: DatabaseRepositoryDefinition,
    ): InputVerificationUseCases =
        InputVerificationUseCases(
            getVideoDataUseCase = GetVideoDataUseCase(networkRepo),
            insertVideo = InsertVideoUseCase(dataBaseRepo),
            setVideoAttributeUseCase = SetVideoAttributeUseCase(dataBaseRepo),
        )

    @Provides
    @Singleton
    fun provideNetworkService(retrofit: Retrofit): YoutubeApiServices = retrofit.create(YoutubeApiServices::class.java)

    @Provides
    @Singleton
    fun provideNetworkRepository(youtubeApiServices: YoutubeApiServices): NetworkRepositoryDefinition =
        NetworkRepositoryImpl(youtubeApiServices)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AddVideoNavigationModule {

    @Binds
    @IntoSet
    abstract fun bindAddNewVideoNavigationProvider(
        provider: AddNewVideoNavigationProvider,
    ): FeatureNavigationProvider
}