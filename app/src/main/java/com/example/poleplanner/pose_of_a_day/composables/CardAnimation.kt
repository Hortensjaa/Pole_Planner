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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.poleplanner.data_structure.Pose
import com.example.poleplanner.ui.theme.DarkPink


// source:
// https://stackoverflow.com/questions/68044576
@Composable
fun CardAnimation(
    pose: Pose = Pose("Placeholder pose")
) {

    var rotated by remember { mutableStateOf(true) }

    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(500), label = ""
    )

    val animateFront by animateFloatAsState(
        targetValue = if (!rotated) 1f else 0f,
        animationSpec = tween(500), label = ""
    )

    val animateBack by animateFloatAsState(
        targetValue = if (rotated) 1f else 0f,
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
                .padding(vertical = 50.dp, horizontal = 30.dp)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 8 * density
                }
                .clickable {
                    if (rotated) rotated = false
                    else {
//                        navController.navigate("${Screen.DetailScreen.route}/${pose.poseName}")
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
                if (rotated) {
                    Back(
                        modifier = Modifier
                        .graphicsLayer {
                            rotationY = rotation
                        })
                } else {
                    Front(
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
