package com.app.core.utils

import com.app.core.data.source.remote.response.CategoriesItem
import com.app.core.data.source.remote.response.MealsDetail
import com.app.core.data.source.remote.response.MealsItem
import com.app.core.domain.usecase.model.Category
import com.app.core.domain.usecase.model.Detail
import com.app.core.domain.usecase.model.Meal

object DataMapper {
    fun mealResponseToDomain(input: List<MealsItem>): List<Meal> {
        val meals = ArrayList<Meal>()
        input.map {
            val meal = Meal(
                thumb = it.thumb,
                idMeal = it.idMeal,
                name = it.name
            )
            meals.add(meal)
        }
        return meals
    }

    fun categoryResponseToDomain(input: List<CategoriesItem>): List<Category> {
        val categories = ArrayList<Category>()
        input.map {
            val category = Category(
                category = it.category,
                description = it.description,
                idCategory = it.idCategory,
                thumb = it.thumb
            )
            categories.add(category)
        }
        return categories
    }

    fun detailResponseToDomain(input: MealsDetail): Detail {
        return input.let {
            Detail(
                video = it.video,
                category = it.category,
                thumb = it.thumb,
                tags = it.tags,
                area = it.area,
                idMeal = it.idMeal,
                name = it.name,
                instructions = it.instructions
            )
        }
    }
}