package com.app.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailMealResponse(

	@field:SerializedName("meals")
	val meals: List<MealsDetail>? = null
)

data class MealsDetail(

	@field:SerializedName("strYoutube")
	val video: String? = null,

	@field:SerializedName("strCategory")
	val category: String? = null,

	@field:SerializedName("strMealThumb")
	val thumb: String? = null,

	@field:SerializedName("strTags")
	val tags: String? = null,

	@field:SerializedName("strArea")
	val area: String? = null,

	@field:SerializedName("idMeal")
	val idMeal: String? = null,

	@field:SerializedName("strMeal")
	val name: String? = null,

	@field:SerializedName("strInstructions")
	val instructions: String? = null
)
