package com.app.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.core.data.source.local.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], exportSchema = false, version = 1)
abstract class MealDatabase : RoomDatabase() {
    abstract fun mealDao() : MealDao
}