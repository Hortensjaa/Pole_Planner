package com.example.poleplanner.navbar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.poleplanner.pose_adding_view.PoseAddingEvent
import com.example.poleplanner.pose_adding_view.composables.PoseAddingScreen
import com.example.poleplanner.pose_detail_view.DetailEvent
import com.example.poleplanner.pose_detail_view.DetailState
import com.example.poleplanner.pose_detail_view.composables.PoseDetailScreen
import com.example.poleplanner.pose_of_a_day.DayViewModel
import com.example.poleplanner.pose_of_a_day.composables.PoseOfDayScreen
import com.example.poleplanner.poses_list_view.AllPosesState
import com.example.poleplanner.poses_list_view.PoseEvent
import com.example.poleplanner.poses_list_view.composables.AllScreen
import com.example.poleplanner.poses_list_view.composables.SavedScreen
import kotlin.reflect.KFunction1

sealed class Screen(val route: String) {
    object AllPosesScreen : Screen("all_screen")
    object SavedScreen: Screen("saved_screen")
    object DetailScreen: Screen("detail_screen")
    object PoseOfDayScreen: Screen("pose_of_day")
    object PoseAddingScreen: Screen("add_pose_screen")
}

@Composable
fun Navigation(
    navController: NavHostController,
    posesState: AllPosesState,
    posesOnEvent: KFunction1<PoseEvent, Unit>,
    detailState: DetailState,
    detailOnEvent: KFunction1<DetailEvent, Unit>,
    addingOnEvent: KFunction1<PoseAddingEvent, Unit>,
    dayVM: DayViewModel
){
    NavHost(navController = navController, startDestination = Screen.PoseOfDayScreen.route) {
        composable(route = Screen.AllPosesScreen.route) {
            AllScreen(posesState, posesOnEvent, navController)
        }
        composable(route = Screen.SavedScreen.route) {
            SavedScreen(posesState, posesOnEvent, navController)
        }
        composable(route = Screen.DetailScreen.route + "/{poseName}") {
            backStackEntry ->
            val poseName = backStackEntry.arguments?.getString("poseName")
            PoseDetailScreen(poseName, detailOnEvent, detailState, navController)
        }
        composable(route = Screen.PoseOfDayScreen.route) {
            PoseOfDayScreen(dayVM, navController)
        }
        composable(route = Screen.PoseAddingScreen.route) {
            PoseAddingScreen(addingOnEvent, navController)
        }
    }
}