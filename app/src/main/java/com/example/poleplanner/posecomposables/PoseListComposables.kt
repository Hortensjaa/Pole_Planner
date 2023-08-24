package com.example.poleplanner.posecomposables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.poleplanner.data_structure.AppDatabase
import com.example.poleplanner.data_structure.Pose
import com.example.poleplanner.ui.theme.AlmostWhite

@Composable
fun PoseListContent(database: AppDatabase) {
    var poseList by remember { mutableStateOf<List<Pose>>(emptyList()) }

    // Use LaunchedEffect to fetch data
    LaunchedEffect(database) {
        val poses = database.poseDao().getAll()
        poseList = poses
    }

    // Display the list when data is available
    PoseList(poseList = poseList)
}

@Preview
@Composable
// source:
// https://developer.android.com/jetpack/compose/lists
fun PoseList(
    poseList: List<Pose> =
        listOf(
            Pose(name="Brass Sit"), Pose(name="Gemini"),
            Pose(name="Aysha"), Pose(name="Scorpio"),
            Pose(name="Fireman spin"), Pose(name="Invert"))
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)) {
        items(poseList) {
            pose ->
            Box (
                modifier = Modifier
                    .padding(5.dp)
                    .background(color = AlmostWhite)
            ) {
                PoseListItem(pose)
            }
        }
    }
}