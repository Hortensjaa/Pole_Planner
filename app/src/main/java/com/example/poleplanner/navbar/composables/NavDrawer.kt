package com.example.poleplanner.navbar.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.poleplanner.navbar.MenuItemsList
import com.example.poleplanner.navbar.Screen
import com.example.poleplanner.poses_list_view.PosesViewModel
import com.example.poleplanner.ui.theme.NavigationDrawerComposeTheme
import kotlinx.coroutines.launch

@Composable
fun NavDrawer(
    posesViewModel: PosesViewModel,
    navController: NavController,
    content: @Composable (Modifier) -> Unit
)
{
    NavigationDrawerComposeTheme {
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        val curScreen = navController.currentBackStackEntryAsState().value?.destination?.route

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                AppBar(
                    onNavigationIconClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    },
                    backAction = {
                        if (Screen.DetailScreen.route in curScreen.toString()) {
                            navController.navigate(Screen.AllPosesScreen.route)
                        } else navController.popBackStack()
                    }
                )
            },
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
            drawerContent = {
                DrawerHeader()
                DrawerBody(
                    items = MenuItemsList(posesViewModel, navController).menu,
                    onItemClick = {
                        it.onClick()
                    }
                )
            }
        ) { padding ->
            content(Modifier.padding(padding))
        }
    }
}