package com.db.recipeapp.core.data.repositories

import com.db.recipeapp.core.data.remote.RecipeResponse
import com.db.recipeapp.helper.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RecipeRepoImpl @Inject constructor(
    private val recipeDataSource: RecipeDataSource
) : RecipeRepo {
    override suspend fun getRecipes(): Flow<Result<RecipeResponse>> {
        return flow {
            try {
                val res = recipeDataSource.getRecipes()
                emit(Result.success(res))
            }catch (e:Exception){
                emit(Result.failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }
}