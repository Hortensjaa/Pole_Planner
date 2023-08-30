package com.example.poleplanner.navbar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.poleplanner.ComboMakerScreen
import com.example.poleplanner.MainScreen
import com.example.poleplanner.pose_detail_view.DetailViewModel
import com.example.poleplanner.pose_detail_view.PoseDetailState
import com.example.poleplanner.pose_detail_view.composables.PoseDetailScreen
import com.example.poleplanner.poses_list_view.AllPosesState
import com.example.poleplanner.poses_list_view.PosesViewModel
import com.example.poleplanner.poses_list_view.composables.AllScreen
import com.example.poleplanner.poses_list_view.composables.SavedScreen

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object AllPosesScreen : Screen("all_screen")
    object SavedScreen: Screen("saved_screen")
    object ComboMakerScreen: Screen("combo_screen")
    object DetailScreen: Screen("detail_screen")
}

@Composable
fun Navigation(
    navController: NavHostController,
    posesState: AllPosesState,
    poseVM: PosesViewModel,
    detailState: PoseDetailState,
    detailVM: DetailViewModel
){
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen()
        }
        composable(route = Screen.AllPosesScreen.route) {
            AllScreen(posesState, poseVM, navController)
        }
        composable(route = Screen.SavedScreen.route) {
            SavedScreen()
        }
        composable(route = Screen.ComboMakerScreen.route) {
            ComboMakerScreen()
        }
        composable(route = Screen.DetailScreen.route + "/{poseName}") {
            backStackEntry ->
            val poseName = backStackEntry.arguments?.getString("poseName")
            PoseDetailScreen(poseName, detailVM, detailState)
        }

    }
}