package com.example.attribute_management_sdk.domain

import com.example.core_sdk.data.database.models.AttributeDO
import com.example.core_sdk.domain.repodefinitions.DatabaseRepositoryDefinition
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class AttributeScreenUseCases(
    val getAllAttributesUseCase: GetAllAttributesUseCase,
    val insertNewAttributeUseCase: SetNewAttributeUseCase,
    val deleteAttributeUseCase: DeleteAttributeUseCase,
)

class SetNewAttributeUseCase
    @Inject
    constructor(
        private val repository: DatabaseRepositoryDefinition,
    ) {
        suspend operator fun invoke(attributeName: String) {
            repository.insertNewAttribute(attributeName)
        }
    }

class GetAllAttributesUseCase
    @Inject
    constructor(
        private val repository: DatabaseRepositoryDefinition,
    ) {
        suspend operator fun invoke(): Flow<List<AttributeDO>> = repository.getAllAttributes()
    }

class DeleteAttributeUseCase
    @Inject
    constructor(
        private val repository: DatabaseRepositoryDefinition,
    ) {
        suspend operator fun invoke(attributeName: String) {
            repository.deleteAttribute(attributeName)
        }
    }
