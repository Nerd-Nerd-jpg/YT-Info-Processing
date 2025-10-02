package com.example.attribute_management_sdk.presentation.attributescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attribute_management_sdk.domain.AttributeScreenUseCases
import com.example.ytinfoprocessing.data.database.models.AttributeDO
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = AttributeViewModel.Factory::class)
class AttributeViewModel
    @AssistedInject
    constructor(
        private val attributeScreenUseCases: AttributeScreenUseCases,
        @Assisted private val isManagementMode: Boolean,
    ) : ViewModel() {
        @AssistedFactory
        interface Factory {
            fun create(managementMode: Boolean): AttributeViewModel
        }

        private val _uiState = MutableStateFlow(AttributeScreenState())
        val uiState: StateFlow<AttributeScreenState> = _uiState.asStateFlow()

        suspend fun getAllAttributes() {
            _uiState.update { it.copy(isLoading = true) }

            attributeScreenUseCases
                .getAllAttributesUseCase()
                .catch {
                    _uiState.update {
                        it.copy(hasError = true)
                    }
                }.collect { attributes ->
                    _uiState.update {
                        it.copy(
                            completeAttributeList = attributes,
                            managementMode = isManagementMode,
                            displayedAttributeList = attributes,
                        )
                    }
                }

            _uiState.update { it.copy(isLoading = false) }
        }

        fun editSearchBar(text: String) {
            _uiState.update {
                it.copy(
                    searchBarText = text,
                    displayedAttributeList =
                        if (text != "") {
                            it.displayedAttributeList.filter { attributeDefinition ->
                                attributeDefinition.attribute.contains(text)
                            }
                        } else {
                            it.completeAttributeList
                        },
                )
            }
        }

        //region attach Attributes mode
        fun attachDetatchAttribute(chosenAttribute: AttributeDO) {
            val attributesInList =
                _uiState
                    .value
                    .selectedAttributes

            val isAttributeSelected = attributesInList.any { it == chosenAttribute }

            if (!isManagementMode) {
                val newList = attributesInList.toMutableList()
                if (isAttributeSelected) {
                    newList.remove(chosenAttribute)
                } else {
                    newList.add(chosenAttribute)
                }

                _uiState.update { it.copy(selectedAttributes = newList) }
            }
        }
        //endregion

        //region manage Attributes mode
        fun insertNewAttribute(newAttribute: String) {
            viewModelScope.launch(Dispatchers.IO) {
                attributeScreenUseCases.insertNewAttributeUseCase(newAttribute)
                delay(300) // Brah, be better pls
                getAllAttributes()
            }
        }

        fun deleteAttribute(attribute: String) {
            viewModelScope.launch(Dispatchers.IO) {
                attributeScreenUseCases.deleteAttributeUseCase(attribute)
                delay(300) // Brah, be better pls
                getAllAttributes()
            }
        }
        //endregion
    }

data class AttributeScreenState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val searchBarText: String = "",
    val completeAttributeList: List<AttributeDO> = arrayListOf(),
    val displayedAttributeList: List<AttributeDO> = arrayListOf(),
    val managementMode: Boolean = true,
    val selectedAttributes: List<AttributeDO> = arrayListOf(),
)
