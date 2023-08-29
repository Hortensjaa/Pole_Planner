package com.example.poleplanner.poses_list_view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.poleplanner.poses_list_view.AllPosesState
import com.example.poleplanner.poses_list_view.PosesViewModel
import com.example.poleplanner.ui.theme.BottomSheetComposeTheme

//todo: zrób coś żeby to nie było takie obrzydliwe
// source:
// https://github.com/philipplackner/BottomSheetCompose
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FiltersBar(
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
//                Divider(
//                    color = MaterialTheme.colorScheme.primary,
//                    thickness = (padding/2).dp)
                Text(
                    text = "filtry",
                    color = MaterialTheme.colorScheme.background,
                    lineHeight = height.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.primary)
                        .padding(padding.dp)
                    )
                FiltersBarContent(state, viewModel)
            },
            sheetPeekHeight = (height + 2 * padding).dp
        ) {
            content()
        }
    }
}

