package com.example.poleplanner.navbar

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.poleplanner.ComboMakerScreen
import com.example.poleplanner.MainScreen
import com.example.poleplanner.SavedScreen
import com.example.poleplanner.data_structure.AppDatabase
import com.example.poleplanner.posecomposables.PoseListContent

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object AllPosesScreen : Screen("all_screen")
    object SavedScreen: Screen("saved_screen")
    object ComboMakerScreen: Screen("combo_screen")
}

@Composable
fun Navigation(database: AppDatabase){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screen.AllPosesScreen.route) {
            PoseListContent(database = database)
        }
        composable(route = Screen.SavedScreen.route) {
            SavedScreen()
        }
        composable(route = Screen.ComboMakerScreen.route) {
            ComboMakerScreen()
        }
    }
}