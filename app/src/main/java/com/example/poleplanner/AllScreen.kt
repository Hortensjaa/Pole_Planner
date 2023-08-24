package com.example.poleplanner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.poleplanner.data_structure.AppDatabase
import com.example.poleplanner.data_structure.Pose
import com.example.poleplanner.posecomposables.PoseList

@Composable
fun AllScreen(database: AppDatabase) {
    var poseList by remember { mutableStateOf<List<Pose>>(emptyList()) }

    // Use LaunchedEffect to fetch data
    LaunchedEffect(database) {
        val poses = database.poseDao().getAll()
        poseList = poses
    }

    PoseList(poseList = poseList)
}
