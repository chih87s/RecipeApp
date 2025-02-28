package com.db.recipeapp.core.data.repositories

import com.db.recipeapp.core.data.remote.RecipeResponse

interface RecipeDataSource {
    suspend fun getRecipes(): RecipeResponse
}

