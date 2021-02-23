package com.app.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "previewFavorite")
data class PreviewFavorite(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "previewId")
    val previewId: Int = 0,

    @ColumnInfo(name = "idMeal")
    val idMeal: String,

    @ColumnInfo(name = "thumb")
    val thumb: String? = null,

    @ColumnInfo(name = "name")
    val name: String? = null
)

@Entity(tableName = "detailFavorite")
data class DetailFavorite (
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "detailId")
    val detailId: Int = 0,

    @ColumnInfo(name = "previewId")
    val previewId: Int,

    @ColumnInfo(name = "video")
    val video: String? = null,

    @ColumnInfo(name = "category")
    val category: String? = null,

    @ColumnInfo(name = "tags")
    val tags: String? = null,

    @ColumnInfo(name = "area")
    val area: String? = null,

    @ColumnInfo(name = "instruction")
    val instructions: String? = null
)