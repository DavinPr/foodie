package com.app.core.domain.usecase

import com.app.core.data.Resource
import com.app.core.domain.usecase.model.Category
import com.app.core.domain.usecase.model.Detail
import com.app.core.domain.usecase.model.Favorite
import com.app.core.domain.usecase.model.Meal
import kotlinx.coroutines.flow.Flow

interface MealUseCase {
    fun getAllMeals(category: String) : Flow<Resource<List<Meal>>>

    fun getAllCategories() : Flow<Resource<List<Category>>>

    fun getDetailMeal(id : String) : Flow<Resource<Detail>>

    fun getAllFavorites() : Flow<Resource<List<Favorite>>>

    fun insertFavorite(favorite: Favorite)

    fun deleteFavorite(favorite: Favorite)

    fun checkFavorited(id: String) : Flow<Boolean>
}