package com.example.poleplanner.posecomposables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.poleplanner.data_structure.Pose
import com.example.poleplanner.ui.theme.AlmostWhite


// source:
// https://developer.android.com/jetpack/compose/lists
@Preview
@Composable
fun PoseList(
    poseList: List<Pose> =
        listOf(
            Pose(name="Brass Sit"), Pose(name="Gemini"),
            Pose(name="Aysha"), Pose(name="Scorpio"),
            Pose(name="Fireman spin"), Pose(name="Invert")
        )
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