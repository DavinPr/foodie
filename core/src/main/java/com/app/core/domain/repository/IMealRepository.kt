package com.app.core.domain.repository

import com.app.core.data.Resource
import com.app.core.domain.usecase.model.Category
import com.app.core.domain.usecase.model.Detail
import com.app.core.domain.usecase.model.Favorite
import com.app.core.domain.usecase.model.Meal
import kotlinx.coroutines.flow.Flow

interface IMealRepository {
    fun getListMeal(category: String): Flow<Resource<List<Meal>>>

    fun getListCategory(): Flow<Resource<List<Category>>>

    fun getDetailMeal(id: String): Flow<Resource<Detail>>

    fun getListFavorite(): Flow<Resource<List<Favorite>>>

    fun insertFavorite(favorite: Favorite)

    fun deleteFavorite(favorite: Favorite)

    fun checkFavorited(id: String): Flow<Boolean>
}