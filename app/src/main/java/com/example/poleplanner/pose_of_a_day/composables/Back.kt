package com.example.poleplanner.pose_of_a_day.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun Back(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text("to jest tył")
    }
}