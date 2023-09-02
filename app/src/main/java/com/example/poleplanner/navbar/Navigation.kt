package com.example.poleplanner.navbar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.poleplanner.pose_detail_view.DetailState
import com.example.poleplanner.pose_detail_view.DetailViewModel
import com.example.poleplanner.pose_detail_view.composables.PoseDetailScreen
import com.example.poleplanner.pose_of_a_day.DayState
import com.example.poleplanner.pose_of_a_day.DayViewModel
import com.example.poleplanner.pose_of_a_day.composables.PoseOfDayScreen
import com.example.poleplanner.poses_list_view.AllPosesState
import com.example.poleplanner.poses_list_view.PosesViewModel
import com.example.poleplanner.poses_list_view.composables.AllScreen
import com.example.poleplanner.poses_list_view.composables.SavedScreen

sealed class Screen(val route: String) {
    object AllPosesScreen : Screen("all_screen")
    object SavedScreen: Screen("saved_screen")
    object DetailScreen: Screen("detail_screen")
    object PoseOfDayScreen: Screen("pose_of_day")
}

@Composable
fun Navigation(
    navController: NavHostController,
    posesState: AllPosesState,
    poseVM: PosesViewModel,
    detailState: DetailState,
    detailVM: DetailViewModel,
    dayState: DayState,
    dayVM: DayViewModel
){
    NavHost(navController = navController, startDestination = Screen.PoseOfDayScreen.route) {
        composable(route = Screen.AllPosesScreen.route) {
            AllScreen(posesState, poseVM, navController)
        }
        composable(route = Screen.SavedScreen.route) {
            SavedScreen(posesState, poseVM, navController)
        }
        composable(route = Screen.DetailScreen.route + "/{poseName}") {
            backStackEntry ->
            val poseName = backStackEntry.arguments?.getString("poseName")
            PoseDetailScreen(poseName, detailVM, detailState)
        }
        composable(route = Screen.PoseOfDayScreen.route) {
            PoseOfDayScreen(dayState, dayVM, navController)
        }
    }
}