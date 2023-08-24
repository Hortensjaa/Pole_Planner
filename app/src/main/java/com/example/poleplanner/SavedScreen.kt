package com.example.poleplanner

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.poleplanner.data_structure.AppDatabase
import com.example.poleplanner.data_structure.Pose
import com.example.poleplanner.posecomposables.PoseList

// future: tworzenie kolekcji
@Composable
fun SavedScreen(database: AppDatabase) {
    var poseList by remember { mutableStateOf<List<Pose>>(emptyList()) }

    LaunchedEffect(database) {
        val poses = database.poseDao().getSaved()
        poseList = poses
    }

    if (poseList.isNotEmpty()) {
        PoseList(poseList = poseList)
    } else {
        Text(text = "Nie masz jeszcze zapisanych żadnych figur.")
    }
}