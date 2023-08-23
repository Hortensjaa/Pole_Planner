package com.example.poleplanner.posecomposables

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.poleplanner.data_structure.AppDatabase
import com.example.poleplanner.data_structure.Pose

@Composable
fun PoseListContent(database: AppDatabase) {
    var poseList by remember { mutableStateOf<List<Pose>>(emptyList()) }

    // Use LaunchedEffect to fetch data
    LaunchedEffect(database) {
        val poses = database.poseDao().getAll()
        poseList = poses
        Log.d("a78sdhs71", poseList.toString())
    }

    // Display the list when data is available
    PoseList(poseList = poseList)
}

@Composable
fun PoseList(poseList: List<Pose>) {
    LazyColumn {
        items(poseList) { pose ->
            PoseListItem(pose)
        }
    }
}
