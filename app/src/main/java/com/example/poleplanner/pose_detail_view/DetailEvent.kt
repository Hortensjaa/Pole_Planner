package com.example.poleplanner.pose_detail_view

sealed interface DetailEvent {

//    data class ChangePose(
//        val poseName: String
//    ): DetailEvent

    object changeNotes : DetailEvent
    object changeDescription : DetailEvent

}