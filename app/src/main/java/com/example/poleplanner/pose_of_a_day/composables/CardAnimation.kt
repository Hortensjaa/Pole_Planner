package com.example.poleplanner.pose_of_a_day.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.poleplanner.data_structure.models.Day
import com.example.poleplanner.navbar.Screen
import com.example.poleplanner.pose_of_a_day.DayViewModel
import java.time.LocalDate


// source:
// https://stackoverflow.com/questions/68044576
@Composable
fun CardAnimation(
    dayVM: DayViewModel,
    navController: NavController
) {

    var lastDay by remember { mutableStateOf<Day?>(Day()) }
    var covered by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val day = dayVM.getLastDay()
        lastDay = day
        covered = lastDay!!.covered
        if (lastDay!!.date != LocalDate.now()) {
            dayVM.getNewPose()
            lastDay = dayVM.getLastDay()
            covered = lastDay!!.covered
        }
    }

    val rotation by animateFloatAsState(
        targetValue = if (covered) 180f else 0f,
        animationSpec = tween(500), label = ""
    )

    val animateFront by animateFloatAsState(
        targetValue = if (!covered) 1f else 0f,
        animationSpec = tween(500), label = ""
    )

    val animateColor by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.primary,
        animationSpec = tween(500), label = ""
    )

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            Modifier
                .fillMaxSize()
                .padding(top = 20.dp, bottom = 50.dp, start = 30.dp, end = 30.dp)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 8 * density
                }
                .clickable {
                    if (covered) {
                        dayVM.uncoverLastDay()
                        covered = false
                    } else {
                        navController.navigate(
                            "${Screen.DetailScreen.route}/${lastDay!!.poseOfDayName}"
                        )
                    }
                },
            backgroundColor = animateColor
        )
        {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (covered) {
                    Back(
                        modifier = Modifier
                        .graphicsLayer {
                            rotationY = rotation
                        })
                } else {
                    Front(
                        dayVM = dayVM,
                        poseName = lastDay!!.poseOfDayName,
                        modifier = Modifier
                            .graphicsLayer {
                                alpha = animateFront
                                rotationY = rotation
                            })
                }
            }

        }
    }
}
