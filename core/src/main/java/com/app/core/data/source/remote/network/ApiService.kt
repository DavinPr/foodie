package com.app.core.data.source.remote.network

import com.app.core.data.source.remote.response.CategoriesResponse
import com.app.core.data.source.remote.response.DetailMealResponse
import com.app.core.data.source.remote.response.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("filter.php")
    suspend fun getAllMeals(@Query("c") category : String) : MealResponse

    @GET("categories.php")
    suspend fun getAllCategories() : CategoriesResponse

    @GET("lookup.php")
    suspend fun getDetailMeal(@Query("i") id: String) : DetailMealResponse
}