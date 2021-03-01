package com.app.core.data.source.local

import com.app.core.data.source.local.entity.FavoriteEntity
import com.app.core.data.source.local.room.MealDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val mealDao: MealDao) {
    fun getAllFavorite(): Flow<List<FavoriteEntity>> = mealDao.getAllFavorite()

    fun insertFavorite(favoriteEntity: FavoriteEntity) =
        mealDao.insertFavorite(favoriteEntity)

    fun deleteFavorite(favoriteEntity: FavoriteEntity) = mealDao.deleteFavorite(favoriteEntity)

    fun deleteFavorite(id: String) =
        mealDao.deleteFavorite(id)

    fun checkFavorite(id: String): Flow<Boolean> = mealDao.isFavorited(id)
}