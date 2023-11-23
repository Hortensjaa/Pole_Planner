package com.example.poleplanner.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun TagsListItem(
    tagName: String = "statyczne",
    isChecked: Boolean = false,
    action: () -> Unit = {}
) {
    Row (
        modifier = Modifier
            .padding(horizontal = 5.dp)
    )
    {
        val frame = if (isChecked) MaterialTheme.colorScheme.background
        else Color.LightGray
        val filling = if (isChecked) MaterialTheme.colorScheme.primary
        else Color.White
        val textColor = if (isChecked) MaterialTheme.colorScheme.background
        else Color.DarkGray
        Text(
            text = "#${tagName.lowercase()}",
            color = textColor,
            modifier = Modifier
                .padding(5.dp)
                .clickable { action() }
                .background(color = frame)
                .padding(2.dp)
                .background(color = filling)
                .padding(vertical = 3.dp, horizontal = 5.dp)
        )
    }
}