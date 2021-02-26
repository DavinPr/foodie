package com.app.foodie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.app.core.data.Resource
import com.app.core.domain.usecase.MealUseCase
import com.app.core.domain.usecase.model.Detail

class DetailViewModel(private val useCase: MealUseCase) : ViewModel() {
    fun getDetailMeal(id : String) : LiveData<Resource<Detail>> = useCase.getDetailMeal(id).asLiveData()
}