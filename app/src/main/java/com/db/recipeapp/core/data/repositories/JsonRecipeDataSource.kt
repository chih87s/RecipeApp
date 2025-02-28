package com.db.recipeapp.core.data.repositories

import com.db.recipeapp.core.data.remote.RecipeResponse
import com.db.recipeapp.helper.JsonParser
import javax.inject.Inject

class JsonRecipeDataSource @Inject constructor(
    private val jsonParser: JsonParser
):RecipeDataSource{
    override suspend fun getRecipes(): RecipeResponse {
        return jsonParser.parseJsonFromFile("recipesSample.json", RecipeResponse::class.java)
    }

}