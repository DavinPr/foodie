package com.app.foodie.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.app.core.data.Resource
import com.app.core.domain.usecase.MealUseCase
import com.app.core.domain.usecase.model.Category
import com.app.core.domain.usecase.model.Meal

class DashboardViewModel(private val useCase: MealUseCase) : ViewModel() {

    fun getAllMeals(category: String): LiveData<Resource<List<Meal>>> =
        useCase.getAllMeals(category).asLiveData()

    fun getAllCategories(): LiveData<Resource<List<Category>>> =
        useCase.getAllCategories().asLiveData()

}