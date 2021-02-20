package com.app.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MealResponse(
	@field:SerializedName("meals")
	val meals: List<MealsItem>? = null
)

data class MealsItem(

	@field:SerializedName("strMealThumb")
	val thumb: String? = null,

	@field:SerializedName("idMeal")
	val idMeal: String? = null,

	@field:SerializedName("strMeal")
	val name: String? = null
)
