package com.example.poleplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.poleplanner.data_structure.AppDatabase
import com.example.poleplanner.navbar.NavDrawer
import com.example.poleplanner.navbar.Navigation
import com.example.poleplanner.navbar.Screen

@Composable
fun MainScreen(navController: NavController) {
    Text("Witaj w naszej aplikacji")
    Button(onClick = {
        navController.navigate(Screen.AllPosesScreen.route)
    }) {
        Text(text = "idz do katalogu")
    }
}



class MainActivity : ComponentActivity() {
    private val database by lazy {
        AppDatabase.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavDrawer (navController) { modifier ->
                Column(
                    modifier = modifier
                ) {
                    Navigation(database, navController)
                }
            }
        }
    }
}









