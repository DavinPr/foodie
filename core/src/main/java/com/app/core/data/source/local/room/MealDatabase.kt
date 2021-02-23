package com.app.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.core.data.source.local.entity.DetailFavorite
import com.app.core.data.source.local.entity.PreviewFavorite

@Database(entities = [PreviewFavorite::class, DetailFavorite::class], exportSchema = false, version = 1)
abstract class MealDatabase : RoomDatabase() {
    abstract fun mealDao() : MealDao
}