package com.db.recipeapp.core.data.repositories

import com.db.recipeapp.core.data.remote.RecipeResponse
import kotlinx.coroutines.flow.Flow

interface RecipeRepo {

    suspend fun getRecipes(): Flow<Result<RecipeResponse>>

}