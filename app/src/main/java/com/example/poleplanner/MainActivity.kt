package com.example.poleplanner

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.poleplanner.data_structure.AppDatabase
import com.example.poleplanner.navbar.Navigation
import com.example.poleplanner.navbar.composables.NavDrawer
import com.example.poleplanner.pose_adding_view.PoseAddingViewModel
import com.example.poleplanner.pose_detail_view.DetailViewModel
import com.example.poleplanner.pose_of_a_day.DayViewModel
import com.example.poleplanner.poses_list_view.PosesViewModel
import com.example.poleplanner.ui.theme.PolePlannerTheme


class MainActivity : ComponentActivity() {

    @Deprecated("Deprecated in Java")
    @SuppressLint("WrongConstant")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val selectedUri: Uri = data?.data ?: return
            val takeFlags = data.flags and (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            contentResolver.takePersistableUriPermission(selectedUri, takeFlags)
        }
    }

    private val database by lazy {
        AppDatabase.getInstance(this)
    }

    private val poseVM by viewModels<PosesViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                 override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PosesViewModel(database.poseDao) as T
                }
            }
        }
    )

    private val detailVM by viewModels<DetailViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                 override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DetailViewModel(database.poseDao) as T
                }
            }
        }
    )

    private val dayVM by viewModels<DayViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                 override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DayViewModel(
                        database.poseDao,
                        database.dayDao) as T
                }
            }
        }
    )

    private val addingVM by viewModels<PoseAddingViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                 override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PoseAddingViewModel(
                        database.poseDao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PolePlannerTheme {
                val navController = rememberNavController()
                NavDrawer(poseVM, navController) {
                    val posesState by poseVM.state.collectAsState()
                    val detailState by detailVM.state.collectAsState()
                    Navigation(
                        navController = navController,
                        posesState = posesState,
                        posesOnEvent = poseVM::onEvent,
                        detailState = detailState,
                        detailOnEvent = detailVM::onEvent,
                        addingOnEvent = addingVM::onEvent,
                        dayVM = dayVM
                    )
                }
            }
        }
    }
}









