package com.app.core.data.source.local.room

import androidx.room.*
import com.app.core.data.source.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Query("SELECT * FROM favorite")
    fun getAllFavorite(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorite WHERE idMeal = :id")
    fun deleteFavorite(id: String)

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE idMeal = :id)")
    fun isFavorited(id: String): Flow<Boolean>
}