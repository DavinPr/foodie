package com.app.core.data

import com.app.core.data.source.remote.RemoteDataSource
import com.app.core.data.source.remote.network.ApiResponse
import com.app.core.domain.repository.IMealRepository
import com.app.core.domain.usecase.model.Category
import com.app.core.domain.usecase.model.Detail
import com.app.core.domain.usecase.model.Favorite
import com.app.core.domain.usecase.model.Meal
import com.app.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class MealRepository(
    private val remoteDataSource: RemoteDataSource
) : IMealRepository {
    override fun getListMeal(category: String): Flow<Resource<List<Meal>>> =
        flow {
            when (val apiResponse = remoteDataSource.getAllMeals(category).first()){
                is ApiResponse.Success -> {
                    if (apiResponse.data != null) {
                        val data = DataMapper.mealResponseToDomain(apiResponse.data)
                        emit(Resource.Success(data))
                    }
                }
                is ApiResponse.Empty -> emit(Resource.Success<List<Meal>>(listOf()))
                is ApiResponse.Error -> emit(Resource.Error<List<Meal>>(message = apiResponse.errorMessage))
            }
        }

    override fun getListCategory(): Flow<Resource<List<Category>>> =
        flow {
            emit(Resource.Loading())
            when(val apiResponse = remoteDataSource.getAllCategories().first()){
                is ApiResponse.Success -> {
                    val data = DataMapper.categoryResponseToDomain(apiResponse.data)
                    emit(Resource.Success(data))
                }
                is ApiResponse.Empty ->{
                    emit(Resource.Success<List<Category>>(listOf()))
                }
                is ApiResponse.Error ->
                    emit(Resource.Error<List<Category>>(message = apiResponse.errorMessage))

            }
        }

    override fun getDetailMeal(id: String): Flow<Resource<Detail>> =
        flow {
            emit(Resource.Loading())
            when(val apiResponse = remoteDataSource.getDetailMeal(id).first()){
                is ApiResponse.Success -> {
                    val data = DataMapper.detailResponseToDomain(apiResponse.data)
                    emit(Resource.Success(data))
                }
                is ApiResponse.Empty -> emit(Resource.Success(Detail()))
                is ApiResponse.Error -> emit(Resource.Error<Detail>(message = apiResponse.errorMessage))
            }
        }

    override fun getListFavorite(): Flow<Resource<List<Favorite>>> {
        TODO("Not yet implemented")
    }

    override fun insertFavorite(favorite: Favorite) {
        TODO("Not yet implemented")
    }

    override fun deleteFavorite(favorite: Favorite) {
        TODO("Not yet implemented")
    }

    override fun checkFavorited(id: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

}