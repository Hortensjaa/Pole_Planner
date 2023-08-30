package com.example.poleplanner.navbar.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import com.example.poleplanner.ui.theme.Typography

// source:
// https://github.com/philipplackner/NavigationDrawerCompose
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    onNavigationIconClick: () -> Unit,
    backAction: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Pole planner",
                style = Typography.titleMedium,
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Toggle drawer"
                )
            }
        },
        actions = {
                  IconButton(onClick = { backAction() }) {
                      Icon(
                          imageVector = Icons.Default.ArrowBackIos,
                          contentDescription = "Toggle drawer"
                      )
                  }
        },
        colors = topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
    )
}