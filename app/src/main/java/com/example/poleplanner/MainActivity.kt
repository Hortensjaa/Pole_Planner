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
import com.example.poleplanner.data_structure.AppDatabase
import com.example.poleplanner.navbar.Navigation
import com.example.poleplanner.navbar.composables.NavDrawer
import com.example.poleplanner.poses_list_view.PosesViewModel
import com.example.poleplanner.ui.theme.PolePlannerTheme

@Composable
fun MainScreen() {
    Text("Witaj w naszej aplikacji")
}



class MainActivity : ComponentActivity() {
    private val database by lazy {
        AppDatabase.getInstance(this)
    }

    private val poseVM by viewModels<PosesViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                 override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PosesViewModel(database.poseDao, database.poseTagDao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PolePlannerTheme {
                val navController = rememberNavController()
                NavDrawer(navController) {
                    val posesState by poseVM.state.collectAsState()
                    Navigation(
                        navController = navController,
                        posesState = posesState,
                        poseVM = poseVM
                    )
                }
            }
        }
    }
}









