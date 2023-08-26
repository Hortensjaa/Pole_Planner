package com.example.poleplanner.navbar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.poleplanner.AllScreen
import com.example.poleplanner.ComboMakerScreen
import com.example.poleplanner.MainScreen
import com.example.poleplanner.SavedScreen
import com.example.poleplanner.data_structure.AllPosesState
import com.example.poleplanner.data_structure.PoseEvent
import kotlin.reflect.KFunction1

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object AllPosesScreen : Screen("all_screen")
    object SavedScreen: Screen("saved_screen")
    object ComboMakerScreen: Screen("combo_screen")
}

@Composable
fun Navigation(
    navController: NavHostController,
    state: AllPosesState,
    onEvent: KFunction1<PoseEvent, Unit>
){
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen()
        }
        composable(route = Screen.AllPosesScreen.route) {
            AllScreen(state, onEvent)
        }
        composable(route = Screen.SavedScreen.route) {
            SavedScreen()
        }
        composable(route = Screen.ComboMakerScreen.route) {
            ComboMakerScreen()
        }
    }
}