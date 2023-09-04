package com.example.poleplanner.data_structure

import com.example.poleplanner.data_structure.models.Difficulty
import com.example.poleplanner.data_structure.models.Pose
import com.example.poleplanner.data_structure.models.Tag

object InitialData {
    val stat = Tag(tagName = "Statyczne")
    val dyn = Tag(tagName = "Dynamiczne")
    val split = Tag(tagName = "Szpagaty")
    val inv = Tag(tagName = "Inverty")
    val duet = Tag(tagName = "Duety")
    val spin = Tag(tagName = "Spiny")

    val tags:
            List<Tag> = listOf(stat, dyn, split, inv, duet, spin)

    val poses_with_tags:
        Map<Pose, List<Tag>> =
            mapOf(
                Pose(poseName = "Gemini", description = "Description 1", difficulty = Difficulty.INTERMEDIATE)
                    to listOf(stat),
                Pose(poseName = "Aysha", description = "Description 2", difficulty = Difficulty.INTERMEDIATE)
                        to listOf(dyn),
                Pose(poseName = "Invert", difficulty = Difficulty.ADVANCED) to listOf(dyn, spin),
                Pose(poseName = "Ballerina", description = "Description 4") to listOf(dyn),
                Pose(poseName = "Brass Monkey") to listOf(stat),
                Pose(poseName = "Fireman spin") to listOf(split, stat, duet),
                Pose(poseName = "Jade") to listOf(stat),
                Pose(poseName = "Scorpio") to listOf(dyn),
                Pose(poseName = "Iron X") to listOf(stat, duet, duet),
                Pose(poseName = "Pole sit") to listOf(stat, dyn, spin, duet),
                Pose(poseName = "Reverse grab spin") to listOf(),
                Pose(poseName = "Russian Layback") to listOf(stat),
            )
}