package com.app.core.data.source.remote

import com.app.core.data.source.remote.network.ApiResponse
import com.app.core.data.source.remote.network.ApiService
import com.app.core.data.source.remote.response.CategoriesItem
import com.app.core.data.source.remote.response.MealsDetail
import com.app.core.data.source.remote.response.MealsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getAllMeals(category: String): Flow<ApiResponse<List<MealsItem>?>> {
        return flow {
            try {
                val response = apiService.getAllMeals(category)
                val dataArray = response.meals

                if (dataArray != null) {
                    if (dataArray.isNotEmpty()) {
                        emit(ApiResponse.Success(dataArray))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                }
            } catch (ex: Exception) {
                emit(ApiResponse.Error(ex.toString()))
                ex.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAllCategories(): Flow<ApiResponse<List<CategoriesItem>>> {
        return flow {
            try {
                val response = apiService.getAllCategories()
                val dataArray = response.categories

                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (ex: Exception) {
                emit(ApiResponse.Error(ex.toString()))
                ex.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailMeal(id : String) : Flow<ApiResponse<MealsDetail>> {
        return flow {
            try {
                val response = apiService.getDetailMeal(id)
                val dataArray = response.meals
                if (dataArray != null) {
                    if (dataArray.isNotEmpty()){
                        emit(ApiResponse.Success(dataArray.first()))
                    } else{
                        emit(ApiResponse.Empty)
                    }
                }
            } catch (ex : Exception) {
                emit(ApiResponse.Error(ex.toString()))
                ex.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)
    }
}