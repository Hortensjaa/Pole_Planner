package com.example.poleplanner.poses_list_view.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.poleplanner.poses_list_view.PoseEvent
import com.example.poleplanner.poses_list_view.PosesViewModel

// source:
// https://github.com/philipplackner/SearchFieldCompose
@Composable
fun SearchBar (
    viewModel: PosesViewModel,
    searchText: String = "spin"
) {
    OutlinedTextField(
        value = searchText,
        onValueChange = viewModel::onSearchTextChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        singleLine = true,
        leadingIcon = { Icon(Icons.Default.Search, "search") },
        trailingIcon = {
            Icon(Icons.Default.Clear, "delete",
                modifier = Modifier.clickable { viewModel.onEvent(PoseEvent.ClearSearcher) })
        },
        label = { Text("wpisz nazwę figury") },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
        )
    )
}
