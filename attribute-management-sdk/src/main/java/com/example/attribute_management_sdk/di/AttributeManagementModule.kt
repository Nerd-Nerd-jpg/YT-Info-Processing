package com.example.attribute_management_sdk.di

import com.example.attribute_management_sdk.AttributeManagementNavigationProvider
import com.example.attribute_management_sdk.domain.AttributeScreenUseCases
import com.example.attribute_management_sdk.domain.DeleteAttributeUseCase
import com.example.attribute_management_sdk.domain.GetAllAttributesUseCase
import com.example.attribute_management_sdk.domain.SetNewAttributeUseCase
import com.example.core_sdk.domain.repodefinitions.DatabaseRepositoryDefinition
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
annotation class AttributeManagementScope

@Module
@InstallIn(SingletonComponent::class)
object AttributeManagementeModule {
//    @AttributeManagementScope
    @Provides
    fun provideAttributeScreenUseCases(dataBaseRepo: DatabaseRepositoryDefinition): AttributeScreenUseCases =
        AttributeScreenUseCases(
            getAllAttributesUseCase = GetAllAttributesUseCase(dataBaseRepo),
            insertNewAttributeUseCase = SetNewAttributeUseCase(dataBaseRepo),
            deleteAttributeUseCase = DeleteAttributeUseCase(dataBaseRepo),
        )
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AttributeNavigationModule {

    @Binds
    @IntoSet
    abstract fun bindAttributeManagementProvider(
        provider: AttributeManagementNavigationProvider,
    ): FeatureNavigationProvider
}