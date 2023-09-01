package com.example.poleplanner.pose_detail_view.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun ContentHideButton (
    text: String = "Opis",
    action: () -> Unit = {},
    editAction: () -> Unit = {},
    saveAction: () -> Unit = {},
    isOpened: Boolean = false,
    editable: Boolean = false,
    isEditing: Boolean = false
) {
    val icon =  if (isOpened) Icons.Default.KeyboardArrowDown
                else Icons.Default.KeyboardArrowRight
    Button(
        onClick = { action() },
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                Icon(
                    imageVector = icon,
                    contentDescription = "open",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = text,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .align(Alignment.CenterVertically)
                )
            }
            if (editable) {
                if (isEditing) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "save",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.clickable{ saveAction() }
                    )
                }
                else {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "edit",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.clickable{ editAction() }
                    )
                }
            }
        }
    }
}