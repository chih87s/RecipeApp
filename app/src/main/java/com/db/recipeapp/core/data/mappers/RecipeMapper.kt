package com.db.recipeapp.core.data.mappers

import com.db.recipeapp.constants.Constants
import com.db.recipeapp.core.data.local.RecipeUIModel
import com.db.recipeapp.core.data.remote.RecipeResponse

class RecipeMapper {

    fun mapToUIModel(response: RecipeResponse): List<RecipeUIModel> {
        return response.recipes.map { recipe ->
            with(recipe){
                RecipeUIModel(
                    title = dynamicTitle,
                    description = dynamicDescription,
                    thumbnailUrl = Constants.IMAGE_PREFIX + dynamicThumbnail,
                    amountLabel = recipeDetails.amountNumber,
                    prepTime = recipeDetails.prepTime,
                    cookingTime = recipeDetails.cookingTime,
                    ingredients = ingredients.map { it.ingredient }
                )
            }

        }
    }
}