package com.app.foodie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.app.core.data.Resource
import com.app.core.domain.usecase.MealUseCase
import com.app.core.domain.usecase.model.Detail
import com.app.core.domain.usecase.model.Favorite

class DetailViewModel(private val useCase: MealUseCase) : ViewModel() {
    fun getDetailMeal(id : String) : LiveData<Resource<Detail>> = useCase.getDetailMeal(id).asLiveData()

    fun insertFavorite(detail: Detail) {
        val favorite = Favorite(
            idMeal = detail.idMeal ?: "",
            thumb = detail.thumb,
            name = detail.name,
            video = detail.video,
            category = detail.category,
            tags = detail.tags,
            area = detail.area,
            instructions = detail.instructions
        )
        useCase.insertFavorite(favorite)
    }

    fun deleteFavorite(detail: Detail){
        val favorite = Favorite(
            idMeal = detail.idMeal ?: "",
            thumb = detail.thumb,
            name = detail.name,
            video = detail.video,
            category = detail.category,
            tags = detail.tags,
            area = detail.area,
            instructions = detail.instructions
        )
        useCase.deleteFavorite(favorite)
    }

    fun checkFavorited(id : String) : LiveData<Boolean> = useCase.checkFavorited(id).asLiveData()

}