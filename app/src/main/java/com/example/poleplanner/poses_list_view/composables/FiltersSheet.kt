package com.example.poleplanner.poses_list_view.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.poleplanner.poses_list_view.AllPosesState
import com.example.poleplanner.poses_list_view.PosesViewModel
import com.example.poleplanner.ui.theme.BottomSheetComposeTheme

// todo: zrób coś żeby to nie było takie obrzydliwe
// source:
// https://github.com/philipplackner/BottomSheetCompose
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FiltersSheet(
    state: AllPosesState,
    viewModel: PosesViewModel,
    content: @Composable () -> Unit
    ) {
    BottomSheetComposeTheme {
        val sheetState = rememberBottomSheetState(
            initialValue = BottomSheetValue.Collapsed
        )
        val scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = sheetState
        )
        val height = 20
        val padding = 5

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent =
            {
                Text(
                    text = "FILTRY",
                    color = MaterialTheme.colorScheme.primary,
                    lineHeight = height.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding.dp)
                    )
                FiltersSheetContent(state, viewModel)
            },
            sheetPeekHeight = (height + 2 * padding).dp,
            sheetShape = RoundedCornerShape(15.dp),
        ) {
            content()
        }
    }
}

