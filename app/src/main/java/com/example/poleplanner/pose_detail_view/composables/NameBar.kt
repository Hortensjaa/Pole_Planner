package com.example.poleplanner.pose_detail_view.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.poleplanner.ui.theme.AutoResizedText
import com.example.poleplanner.ui.theme.Typography

@Preview
@Composable
fun NameBar (
    poseName: String = "Placeholder",
    saved: Boolean = false,
    action: () -> Unit = {}
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AutoResizedText(
            text = poseName,
            style = Typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .wrapContentSize(align = Alignment.Center)
        )
        Icon(
            imageVector = if (saved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Save pose",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable { action() }
                .size((Typography.titleLarge.fontSize.value * 1.5).dp)
        )
    }

}