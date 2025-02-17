package com.db.recipeapp.core.domain

import com.db.recipeapp.core.data.local.RecipeUIModel
import kotlinx.coroutines.flow.Flow

interface RecipeUseCase {

    suspend fun getFilteredAndSortedRecipes(): Flow<Result<List<RecipeUIModel>>>

}