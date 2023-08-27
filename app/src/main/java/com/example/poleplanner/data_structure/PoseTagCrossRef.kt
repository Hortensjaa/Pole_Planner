package com.example.poleplanner.data_structure

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(primaryKeys = ["poseName", "tagName"])
data class PoseTagCrossRef(
    val poseName: String,
    val tagName: String
)

// lista tagów dla figury
data class PoseWithTags(
    @Embedded val pose: Pose,
    @Relation(
        parentColumn = "poseName",
        entityColumn = "tagName",
        associateBy = Junction(PoseTagCrossRef::class)
    )
    val tags: List<Tag>
)

// lista figur o danym tagu
data class TagWithPoses(
    @Embedded val tag: Tag,
    @Relation(
        parentColumn = "tagName",
        entityColumn = "poseName",
        associateBy = Junction(PoseTagCrossRef::class)
    )
    val poses: List<Pose>
)
