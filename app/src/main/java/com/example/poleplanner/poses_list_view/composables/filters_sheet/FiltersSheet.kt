package com.example.poleplanner.poses_list_view.composables.filters_sheet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.poleplanner.poses_list_view.AllPosesState
import com.example.poleplanner.poses_list_view.PoseEvent
import com.example.poleplanner.ui.theme.BottomSheetComposeTheme


// source:
// https://github.com/philipplackner/BottomSheetCompose
// todo: dodać filtr po figurach dodanych przez uzytkownika
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FiltersSheet(
    state: AllPosesState,
    posesOnEvent: (PoseEvent) -> Unit,
    fabAction: () -> Unit,
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
                Icon(
                    imageVector = Icons.Default.FilterAlt,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(padding.dp)
                )
                FiltersSheetContent(state, posesOnEvent)
            },
            sheetPeekHeight = (height + 2 * padding).dp,
            sheetShape = RoundedCornerShape(15.dp),
            floatingActionButton =
            {
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.primary,
                    onClick = { fabAction() },
                    modifier = Modifier.padding(bottom = 5.dp)
                ) {
                    Icon(Icons.Default.Add, "Add pose")
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) {
            content()
        }
    }
}

