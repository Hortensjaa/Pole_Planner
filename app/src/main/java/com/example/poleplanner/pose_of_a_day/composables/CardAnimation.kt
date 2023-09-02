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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.poleplanner.navbar.Screen
import com.example.poleplanner.pose_of_a_day.DayEvent
import com.example.poleplanner.pose_of_a_day.DayState
import com.example.poleplanner.pose_of_a_day.DayViewModel
import com.example.poleplanner.ui.theme.DarkPink


// source:
// https://stackoverflow.com/questions/68044576
@Composable
fun CardAnimation(
    dayState: DayState,
    dayVM: DayViewModel,
    navController: NavController
) {

    if (dayState.covered) {
        LaunchedEffect(dayState.pose) {
            val pose = dayVM.getNewPose()!!
            dayVM.onEvent(DayEvent.ChangePose(pose))
        }
    }

    val rotation by animateFloatAsState(
        targetValue = if (dayState.covered) 180f else 0f,
        animationSpec = tween(500), label = ""
    )

    val animateFront by animateFloatAsState(
        targetValue = if (!dayState.covered) 1f else 0f,
        animationSpec = tween(500), label = ""
    )

    val animateColor by animateColorAsState(
        targetValue = DarkPink,
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
                    if (dayState.covered) {
                        dayVM.onEvent(DayEvent.UncoverPose)
                    } else {
                        navController.navigate(
                            "${Screen.DetailScreen.route}/${dayState.pose.poseName}")
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
                if (dayState.covered) {
                    Back(
                        modifier = Modifier
                        .graphicsLayer {
                            rotationY = rotation
                        })
                } else {
                    Front(
                        pose = dayState.pose,
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
