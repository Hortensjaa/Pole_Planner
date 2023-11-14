package com.example.poleplanner.pose_adding_view.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun DescriptionField (
) {
    var text1 by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = text1,
        onValueChange = { text1 = it },
        label = { Text("Opis") },
        minLines = 3,
        maxLines = 10,
        modifier = Modifier
            .fillMaxWidth(0.75f)
    )
}