package com.example.poleplanner.navbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.SportsGymnastics
import androidx.navigation.NavController
import com.example.poleplanner.poses_list_view.PoseEvent
import com.example.poleplanner.poses_list_view.PosesViewModel

data class MenuItemsList(
    val posesViewModel: PosesViewModel,
    val navController: NavController
) {
    val menu = listOf(
        MenuItem(
            title = "Ulubione",
            contentDescription = "Figury, do których planujesz wrócić",
            icon = Icons.Default.Favorite,
            onClick = {
                navController.navigate(Screen.SavedScreen.route)
                posesViewModel.onEvent(PoseEvent.ClearState(true))
            }
        ),
        MenuItem(
            title = "Katalog",
            contentDescription = "Alfabetyczna lista wszystkich figur",
            icon = Icons.Default.List,
            onClick = {
                navController.navigate(Screen.AllPosesScreen.route)
                posesViewModel.onEvent(PoseEvent.ClearState(false))
            }
        ),
        MenuItem(
            title = "Figura dnia",
            contentDescription = "Codziennie nowa figurka",
            icon = Icons.Default.SportsGymnastics,
            onClick = { navController.navigate(Screen.PoseOfDayScreen.route) }
        ),
        MenuItem(
            title = "Kolekcje",
            contentDescription = "Grupuj swoje ulubione figury w kolekcje i zapisuj tworzone układy",
            icon = Icons.Default.CollectionsBookmark,
            onClick = {}
        ),
        MenuItem(
            title = "Plan treningów",
            contentDescription = "Zapisuj, co ćwiczyłaś ostatnio i planuj przyszłe treningi",
            icon = Icons.Default.CalendarMonth,
            onClick = {}
        )
    )
}