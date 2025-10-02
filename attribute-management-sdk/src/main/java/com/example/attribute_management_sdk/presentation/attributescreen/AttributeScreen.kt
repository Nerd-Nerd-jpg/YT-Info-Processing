package com.example.attribute_management_sdk.presentation.attributescreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ytinfoprocessing.data.database.models.AttributeDO

@Composable
fun AttributeScreen(viewModel: AttributeViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllAttributes()
    }

    AttributeScreenContent(
        state = uiState,
        onEditSearchBar = { viewModel.editSearchBar(it) },
        onInsertNewAttribute = { viewModel.insertNewAttribute(it) },
        onDeleteAttribute = { viewModel.deleteAttribute(it) },
        onAttachNewAttribute = { viewModel.attachDetatchAttribute(AttributeDO(it)) },
    )
}

//region Attribute Screen Content
@Composable
private fun AttributeScreenContent(
    state: AttributeScreenState,
    onEditSearchBar: (String) -> Unit,
    onInsertNewAttribute: (String) -> Unit,
    onDeleteAttribute: (String) -> Unit,
    onAttachNewAttribute: (String) -> Unit,
) {
    var showAddDialog by remember { mutableStateOf(false) }
    Scaffold { padding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = state.searchBarText,
                    onValueChange = { onEditSearchBar(it) },
                    label = { Text("Search") },
                    modifier = Modifier.weight(5f),
                    trailingIcon = {
                        if (state.managementMode) {
                            Icon(
                                modifier =
                                    Modifier.clickable {
                                        showAddDialog = true
                                    },
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add new Attribute",
                            )
                        }
                    },
                )
            }

            // Item List
            LazyColumn(
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(top = 16.dp),
            ) {
                items(state.displayedAttributeList) { AttributeDO ->
                    ItemCard(
                        isSelected = state.selectedAttributes.any { it == AttributeDO },
                        attribute = AttributeDO,
                        managementMode = state.managementMode,
                        onDeleteAttribute = { onDeleteAttribute(it) },
                        onAttachAttribute = { onAttachNewAttribute(it) },
                    )
                }
            }
        }
    }

    // Add Item Dialog
    if (showAddDialog) {
        AddItemDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { onInsertNewAttribute(it) },
        )
    }
}

@Preview
@Composable
private fun PreviewAttributeScreenContent() {
    val dummyListOfItems =
        arrayListOf(
            AttributeDO("Com Science"),
            AttributeDO("Music"),
            AttributeDO("Music training"),
            AttributeDO("Athletics"),
            AttributeDO("MMA"),
        )

    AttributeScreenContent(
        state =
            AttributeScreenState(
                isLoading = false,
                hasError = false,
                completeAttributeList = dummyListOfItems,
                displayedAttributeList = dummyListOfItems,
                searchBarText = "Search",
                managementMode = true,
                selectedAttributes = arrayListOf(),
            ),
        onEditSearchBar = {},
        onInsertNewAttribute = {},
        onDeleteAttribute = {},
        onAttachNewAttribute = {},
    )
}
//endregion

// region Item Card Composable
@Composable
fun ItemCard(
    isSelected: Boolean,
    attribute: AttributeDO,
    managementMode: Boolean = true,
    onDeleteAttribute: (String) -> Unit = {},
    onAttachAttribute: (String) -> Unit = {},
) {
    val modifier =
        if (managementMode) {
            Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        } else {
            Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clickable { }
        }

    Card(
        modifier = modifier,
        onClick = { onAttachAttribute(attribute.attribute) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = if (isSelected)Color.Yellow else Color.Unspecified,
            ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = attribute.attribute,
                    style = MaterialTheme.typography.titleMedium,
                )

                if (managementMode) {
                    Icon(
                        modifier = Modifier.clickable { onDeleteAttribute(attribute.attribute) },
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewItemCard() {
    ItemCard(
        isSelected = true,
        attribute =
            AttributeDO(
                "I DIDN'T DO IT!!!!",
            ),
        managementMode = false,
        onDeleteAttribute = {},
        onAttachAttribute = {},
    )
}
//endregion

// region Add Item Dialog Composable
@Composable
fun AddItemDialog(
    onDismiss: () -> Unit,
    onAdd: (String) -> Unit,
) {
    var title by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Item") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (title.isNotBlank()) {
                        onAdd(title)
                        onDismiss()
                    }
                },
                enabled = title.isNotBlank(),
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
    )
}

@Preview
@Composable
private fun PreviewAddItemDialog() {
    AddItemDialog({}) {}
}
//endregion
