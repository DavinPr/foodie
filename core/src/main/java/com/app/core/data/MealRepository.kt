package com.app.core.data

import com.app.core.data.source.local.LocalDataSource
import com.app.core.data.source.remote.RemoteDataSource
import com.app.core.data.source.remote.network.ApiResponse
import com.app.core.domain.repository.IMealRepository
import com.app.core.domain.usecase.model.Category
import com.app.core.domain.usecase.model.Detail
import com.app.core.domain.usecase.model.Favorite
import com.app.core.domain.usecase.model.Meal
import com.app.core.utils.AppExecutors
import com.app.core.utils.DataMapper
import kotlinx.coroutines.flow.*

class MealRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
) : IMealRepository {
    override fun getListMeal(category: String): Flow<Resource<List<Meal>>> =
        flow {
            when (val apiResponse = remoteDataSource.getAllMeals(category).first()) {
                is ApiResponse.Success -> {
                    if (apiResponse.data != null) {
                        val data = DataMapper.mealResponseToDomain(apiResponse.data)
                        emit(Resource.Success(data))
                    }
                }
                is ApiResponse.Empty -> emit(Resource.Success<List<Meal>>(emptyList()))
                is ApiResponse.Error -> emit(Resource.Error<List<Meal>>(message = apiResponse.errorMessage))
            }
        }

    override fun getListCategory(): Flow<Resource<List<Category>>> =
        flow {
            emit(Resource.Loading())
            when (val apiResponse = remoteDataSource.getAllCategories().first()) {
                is ApiResponse.Success -> {
                    val data = DataMapper.categoryResponseToDomain(apiResponse.data)
                    emit(Resource.Success(data))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Success<List<Category>>(emptyList()))
                }
                is ApiResponse.Error ->
                    emit(Resource.Error<List<Category>>(message = apiResponse.errorMessage))

            }
        }

    override fun getDetailMeal(id: String): Flow<Resource<Detail>> =
        flow {
            emit(Resource.Loading())
            when (val apiResponse = remoteDataSource.getDetailMeal(id).first()) {
                is ApiResponse.Success -> {
                    val data = DataMapper.detailResponseToDomain(apiResponse.data)
                    emit(Resource.Success(data))
                }
                is ApiResponse.Empty -> emit(Resource.Success(Detail()))
                is ApiResponse.Error -> emit(Resource.Error<Detail>(message = apiResponse.errorMessage))
            }
        }

    override fun getListFavorite(): Flow<Resource<List<Favorite>>> =
        flow {
            emit(Resource.Loading())
            val data = localDataSource.getAllFavorite().map {
                DataMapper.favoriteEntityToDomain(it)
            }
            emitAll(
                data.map {
                    Resource.Success(it)
                }
            )
        }

    override fun insertFavorite(favorite: Favorite) {
        val data = DataMapper.favoriteDomainToEntity(favorite)
        appExecutors.diskIO().execute {
            localDataSource.insertFavorite(data)
        }
    }

    override fun deleteFavorite(favorite: Favorite) {
        val data = DataMapper.favoriteDomainToEntity(favorite)
        appExecutors.diskIO().execute {
            localDataSource.deleteFavorite(data)
        }
    }

    override fun checkFavorited(id: String): Flow<Boolean> = localDataSource.checkFavorite(id)

}