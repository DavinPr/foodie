package com.app.foodie.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.app.core.data.Resource
import com.app.core.domain.usecase.MealUseCase
import com.app.core.domain.usecase.model.Favorite

class FavoriteViewModel(private val useCase: MealUseCase) : ViewModel() {
    fun getAllFavoriteData() : LiveData<Resource<List<Favorite>>> = useCase.getAllFavorites().asLiveData()
}