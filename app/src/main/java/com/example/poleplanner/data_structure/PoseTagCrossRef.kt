package com.example.poleplanner.data_structure

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(primaryKeys = ["poseId", "tagId"])
data class PoseTagCrossRef(
    val poseId: Int,
    val tagId: Int
)

// lista tagów dla figury
data class PoseWithTags(
    @Embedded val pose: Pose,
    @Relation(
        parentColumn = "poseId",
        entityColumn = "tagId",
        associateBy = Junction(PoseTagCrossRef::class)
    )
    val tags: List<Tag>
)

// lista figur o danym tagu
data class TagWithPoses(
    @Embedded val tag: Tag,
    @Relation(
        parentColumn = "tagId",
        entityColumn = "poseId",
        associateBy = Junction(PoseTagCrossRef::class)
    )
    val poses: List<Pose>
)
