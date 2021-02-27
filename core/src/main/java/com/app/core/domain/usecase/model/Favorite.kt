package com.app.core.domain.usecase.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Favorite(
    val detailId: Int,
    val idMeal: String,
    val thumb: String? = null,
    val name: String? = null,
    val video: String? = null,
    val category: String? = null,
    val tags: String? = null,
    val area: String? = null,
    val instructions: String? = null
) : Parcelable