package com.example.poleplanner.data_structure.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
@Parcelize
data class Day (
    @PrimaryKey(autoGenerate = true) val dayId: Int = 0,
    val dateTime: LocalDateTime = LocalDateTime.MIN,
    val poseOfDayName: String = "placeholder pose",
    var covered: Boolean = true
) : Parcelable {

    val createdDateFormatted : String
        get() = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))

}