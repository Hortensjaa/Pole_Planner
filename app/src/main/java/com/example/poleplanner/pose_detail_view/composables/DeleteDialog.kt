package com.example.poleplanner.pose_detail_view.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable


@Composable
fun DeleteDialog(
    deleteAction: () -> Unit,
    dismissAction: () -> Unit
) {
    AlertDialog(
        icon = {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        },
        title = {
            Text(text = "Czy na pewno chcesz usunąć tę figurę?")
        },
        text = {
            Text(text = "Tej akcji nie będzie można cofnąć")
        },
        onDismissRequest = { dismissAction() },
        confirmButton = {
            TextButton(
                onClick = { deleteAction() }
            ) {
                Text("Usuń")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { dismissAction() }
            ) {
                Text("Anuluj")
            }
        }
    )
}
