package com.app.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CategoriesResponse(

	@field:SerializedName("categories")
	val categories: List<CategoriesItem>
)

data class CategoriesItem(

	@field:SerializedName("strCategory")
	val category: String? = null,

	@field:SerializedName("strCategoryDescription")
	val description: String? = null,

	@field:SerializedName("idCategory")
	val idCategory: String? = null,

	@field:SerializedName("strCategoryThumb")
	val thumb: String? = null
)
