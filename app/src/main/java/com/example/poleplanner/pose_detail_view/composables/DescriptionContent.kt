package com.example.poleplanner.pose_detail_view.composables

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.dp
import com.example.poleplanner.pose_detail_view.DetailState
import kotlin.math.roundToInt


@Composable
fun DescriptionContent (
    description: String = "",
    state: DetailState,
    scrollState: ScrollState,
    action: () -> Unit = {}
) {
    // source:
    // https://stackoverflow.com/questions/67586828
    var scrollToPosition by remember { mutableStateOf(0F) }
    ContentHideButton(
        text = "Opis",
        action = { action() },
        isOpened = state.descriptionOpen
    )
    LaunchedEffect(state.descriptionOpen) {
        scrollState.animateScrollTo(scrollToPosition.roundToInt())
    }
    if (state.descriptionOpen) {
        Text(
            text = description,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(10.dp)
                .onGloballyPositioned { coordinates ->
                    scrollToPosition = coordinates.positionInRoot().y
                }
        )
    }
}
