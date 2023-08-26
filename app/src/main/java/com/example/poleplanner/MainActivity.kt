package com.example.poleplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.poleplanner.data_structure.AppDatabase
import com.example.poleplanner.data_structure.AppDatabaseCallback
import com.example.poleplanner.data_structure.PoseViewModel
import com.example.poleplanner.navbar.NavDrawer
import com.example.poleplanner.navbar.Navigation

@Composable
fun MainScreen() {
    Text("Witaj w naszej aplikacji")
}



class MainActivity : ComponentActivity() {
    private val database by lazy {
        AppDatabase.getInstance(this)
    }

    private val viewModel by viewModels<PoseViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                 override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PoseViewModel(database.poseDao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavDrawer (navController) {
                val state by viewModel.state.collectAsState()
                Navigation(
                    navController = navController,
                    state = state,
                    onEvent = viewModel::onEvent)
            }
        }
    }
}









