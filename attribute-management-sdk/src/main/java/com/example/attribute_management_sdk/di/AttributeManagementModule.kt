package com.example.attribute_management_sdk.di

import com.example.attribute_management_sdk.domain.AttributeScreenUseCases
import com.example.attribute_management_sdk.domain.DeleteAttributeUseCase
import com.example.attribute_management_sdk.domain.GetAllAttributesUseCase
import com.example.attribute_management_sdk.domain.SetNewAttributeUseCase
import com.example.core_sdk.domain.repodefinitions.DatabaseRepositoryDefinition
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AttributeManagementeModule {
    @Provides
    fun provideAttributeScreenUseCases(dataBaseRepo: DatabaseRepositoryDefinition): AttributeScreenUseCases =
        AttributeScreenUseCases(
            getAllAttributesUseCase = GetAllAttributesUseCase(dataBaseRepo),
            insertNewAttributeUseCase = SetNewAttributeUseCase(dataBaseRepo),
            deleteAttributeUseCase = DeleteAttributeUseCase(dataBaseRepo),
        )
}
