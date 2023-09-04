package com.example.poleplanner.pose_of_a_day.composables


// source:
// https://stackoverflow.com/questions/68044576
//@Composable
//fun CardAnimation(
////    dayState: DayState,
//    dayVM: DayViewModel,
//    navController: NavController
//) {
//
//    LaunchedEffect(dayState.covered) {
//        if (dayState.covered) {
//            val pose = dayVM.getNewPose()!!
//            dayVM.onEvent(DayEvent.ChangePose(pose))
//        }
//    }
//
//    val rotation by animateFloatAsState(
//        targetValue = if (dayState.covered) 180f else 0f,
//        animationSpec = tween(500), label = ""
//    )
//
//    val animateFront by animateFloatAsState(
//        targetValue = if (!dayState.covered) 1f else 0f,
//        animationSpec = tween(500), label = ""
//    )
//
//    val animateColor by animateColorAsState(
//        targetValue = DarkPink,
//        animationSpec = tween(500), label = ""
//    )
//
//    Box(
//        Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Card(
//            Modifier
//                .fillMaxSize()
//                .padding(top = 20.dp, bottom = 50.dp, start = 30.dp, end = 30.dp)
//                .graphicsLayer {
//                    rotationY = rotation
//                    cameraDistance = 8 * density
//                }
//                .clickable {
//                    if (dayState.covered) {
//                        dayVM.onEvent(DayEvent.UncoverPose)
//                    } else {
//                        navController.navigate(
//                            "${Screen.DetailScreen.route}/${dayState.pose.poseName}")
//                    }
//                },
//            backgroundColor = animateColor
//        )
//        {
//            Column(
//                Modifier.fillMaxSize(),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                if (dayState.covered) {
//                    Back(
//                        modifier = Modifier
//                        .graphicsLayer {
//                            rotationY = rotation
//                        })
//                } else {
//                    Front(
//                        dayVM = dayVM,
//                        poseName = dayState.pose.poseName,
//                        modifier = Modifier
//                            .graphicsLayer {
//                                alpha = animateFront
//                                rotationY = rotation
//                            })
//                }
//            }
//
//        }
//    }
//}
