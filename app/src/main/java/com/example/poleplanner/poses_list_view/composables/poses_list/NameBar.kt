package com.example.poleplanner.poses_list_view.composables.poses_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.poleplanner.ui.theme.AutoResizedText

@Composable
fun NameBar(
    poseName: String = "Pose placeholder",
    saved: Boolean,
    action: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AutoResizedText(
            text = poseName,
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .padding(10.dp)
        )
        Icon(
            imageVector = if (saved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Save pose",
            modifier = Modifier
                .padding(10.dp)
                .clickable { action() },
            tint = MaterialTheme.colorScheme.background,
        )
    }
}