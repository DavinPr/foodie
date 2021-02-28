package com.app.foodie.di

import com.app.core.domain.usecase.MealInteractor
import com.app.core.domain.usecase.MealUseCase
import com.app.foodie.dashboard.DashboardViewModel
import com.app.foodie.detail.DetailViewModel
import com.app.foodie.favorite.FavoriteViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MealUseCase> { MealInteractor(get()) }
}

val viewModelModule = module {
    viewModel { DashboardViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
}