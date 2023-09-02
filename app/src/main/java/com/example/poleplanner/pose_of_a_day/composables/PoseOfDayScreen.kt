package com.example.poleplanner.pose_of_a_day.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import com.example.poleplanner.ui.theme.Typography


@Composable
fun PoseOfDayScreen () {
    Column {
        Text(
            text="Figura dnia",
            textAlign = TextAlign.Center,
            style = Typography.titleMedium
        )
        CardAnimation()
    }
}