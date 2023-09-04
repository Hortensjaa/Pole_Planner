package com.example.poleplanner.pose_of_a_day.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.poleplanner.pose_of_a_day.DayViewModel
import com.example.poleplanner.ui.theme.Typography


@Composable
fun PoseOfDayScreen (
//    dayState: DayState,
    dayVM: DayViewModel,
    navController: NavController
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ){
        Text(
            text = "Figura dnia",
            textAlign = TextAlign.Center,
            style = Typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.primary
        )
        var bool by remember { mutableStateOf(false) }
        Button(onClick = { bool = !bool }) {
            LaunchedEffect(bool) {
                dayVM.getNewPose()
            }
            Text("wylosuj figure dnia")
        }
//        Text(dayState.lastDrawDate.toString())
//        CardAnimation(dayVM, navController)
    }
}