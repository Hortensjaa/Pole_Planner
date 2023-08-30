package com.example.poleplanner.pose_detail_view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.poleplanner.pose_detail_view.DetailEvent
import com.example.poleplanner.pose_detail_view.DetailViewModel
import com.example.poleplanner.pose_detail_view.PoseDetailState


@Composable
fun DescriptionContent (
    description: String = "",
    detailVM: DetailViewModel,
    state: PoseDetailState
) {
    ContentHideButton(
        text = "Opis",
        action = { detailVM.onEvent(DetailEvent.DescriptionChangeVisibility) },
        isOpened = state.descriptionOpen
    )
    if (state.descriptionOpen) {
        Text(
            text = description,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(10.dp)
        )
    }
}