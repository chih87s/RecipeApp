package com.db.recipeapp.core.domain

import com.db.recipeapp.core.data.local.RecipeUIModel
import com.db.recipeapp.core.data.mappers.RecipeMapper
import com.db.recipeapp.core.data.remote.RecipeResponse
import com.db.recipeapp.core.data.repositories.RecipeRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeUseCaseImpl @Inject constructor(
    private val repository: RecipeRepo,
    private val recipeMapper: RecipeMapper
) : RecipeUseCase {

    override suspend fun getFilteredAndSortedRecipes(): Flow<Result<List<RecipeUIModel>>> {
        return repository.getRecipes().map { res ->
            res.fold(
                onSuccess = { response ->
                    try {
                        val sortedAndFilteredRecipes = response.recipes
                            .filter { recipe -> recipe.dynamicTitle.isNotEmpty() }
                            .sortedBy { recipe -> recipe.dynamicTitle }
                        Result.success(recipeMapper.mapToUIModel(RecipeResponse(sortedAndFilteredRecipes)))
                    }catch (e:Exception){
                        Result.failure(e)
                    }
                },
                onFailure = { error ->
                    Result.failure(error)
                }
            )
        }.flowOn(Dispatchers.IO)
    }

}