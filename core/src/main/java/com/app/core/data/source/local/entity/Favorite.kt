package com.app.core.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class Favorite(
    @Embedded
    val preview: PreviewFavorite,

    @Relation(
        parentColumn = "previewId",
        entityColumn = "previewId"
    )
    val detail: DetailFavorite
)