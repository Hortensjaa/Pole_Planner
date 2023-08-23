package com.example.poleplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.poleplanner.data_structure.AppDatabase
import com.example.poleplanner.posecomposables.PoseListContent
import com.example.poleplanner.ui.theme.PolePlannerTheme

class AllPosesActivity : ComponentActivity() {
    private val database by lazy {
        AppDatabase.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PolePlannerTheme {
                // Pass the database instance to PoseListContent
                PoseListContent(database = database)
            }
        }
    }
}
