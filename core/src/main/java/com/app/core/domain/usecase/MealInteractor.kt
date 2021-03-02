package com.app.core.domain.usecase

import com.app.core.data.Resource
import com.app.core.domain.repository.IMealRepository
import com.app.core.domain.usecase.model.Category
import com.app.core.domain.usecase.model.Detail
import com.app.core.domain.usecase.model.Favorite
import com.app.core.domain.usecase.model.Meal
import kotlinx.coroutines.flow.Flow


class MealInteractor(private val mealRepository: IMealRepository) : MealUseCase {
    override fun getAllMeals(category: String): Flow<Resource<List<Meal>>> =
        mealRepository.getListMeal(category)

    override fun getAllCategories(): Flow<Resource<List<Category>>> {
        return mealRepository.getListCategory()
    }

    override fun getDetailMeal(id: String): Flow<Resource<Detail>> =
        mealRepository.getDetailMeal(id)

    override fun getAllFavorites(): Flow<Resource<List<Favorite>>> =
        mealRepository.getListFavorite()

    override fun insertFavorite(favorite: Favorite) = mealRepository.insertFavorite(favorite)

    override fun deleteFavorite(favorite: Favorite) = mealRepository.deleteFavorite(favorite)

    override fun checkFavorited(id: String): Flow<Boolean> = mealRepository.checkFavorited(id)
}