package com.example.poleplanner.navbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.List
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.poleplanner.ui.theme.NavigationDrawerComposeTheme
import kotlinx.coroutines.launch

@Composable
fun NavBar(content: @Composable (Modifier) -> Unit) {
    NavigationDrawerComposeTheme {
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                AppBar(
                    onNavigationIconClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                )
            },
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
            drawerContent = {
                DrawerHeader()
                DrawerBody(
                    items = listOf(
                        MenuItem(
                            title = "Zapisane figury",
                            contentDescription = "Figury, do których planujesz wrócić",
                            icon = Icons.Default.FavoriteBorder
                        ),
                        MenuItem(
                            title = "Katalog figur",
                            contentDescription = "Alfabetyczna lista wszystkich figur",
                            icon = Icons.Default.List
                        ),
                        MenuItem(
                            title = "Losuj",
                            contentDescription = "Wylosuj jedną figurę do zrobienia lub skorzystaj z naszego combo-makera",
                            icon = Icons.Default.Add
                        ),
                    ),
                    onItemClick = {
                        println("Clicked on ${it.title}")
                    }
                )
            }
        ) { padding ->
            // Apply the padding to the content using Modifier.padding
            content(Modifier.padding(padding))
        }
    }
}