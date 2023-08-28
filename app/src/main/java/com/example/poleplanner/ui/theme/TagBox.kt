package com.example.poleplanner.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TagBox (
    tagName: String,
    action: () -> Unit = {},
    textColor: Color = Color.White,
    backgroundColor: Color = AlmostWhite.copy(alpha = 0.3f)
)
{
    Text(text = "#${tagName.lowercase()}",
        maxLines = 1,
        color = textColor,
        modifier = Modifier
            .padding(5.dp)
            .background(color = backgroundColor)
            .padding(vertical = 3.dp, horizontal = 5.dp)
            .clickable {action()}
    )
}