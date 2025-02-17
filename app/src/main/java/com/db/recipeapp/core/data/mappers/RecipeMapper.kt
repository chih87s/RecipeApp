package com.db.recipeapp.core.data.mappers

import com.db.recipeapp.constants.Constants
import com.db.recipeapp.core.data.local.RecipeUIModel
import com.db.recipeapp.core.data.remote.RecipeResponse

class RecipeMapper {

    fun mapToUIModel(response: RecipeResponse): List<RecipeUIModel> {
        return response.recipes.map { recipe ->
            RecipeUIModel(
                title = recipe.dynamicTitle,
                description = recipe.dynamicDescription,
                thumbnailUrl = Constants.IMAGE_PREFIX + recipe.dynamicThumbnail,
                prepTime = recipe.recipeDetails.prepTime,
                cookingTime = recipe.recipeDetails.cookingTime,
                ingredients = recipe.ingredients.map { it.ingredient }
            )
        }
    }
}