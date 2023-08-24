package com.example.poleplanner.navbar

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val title: String,
    val contentDescription: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)