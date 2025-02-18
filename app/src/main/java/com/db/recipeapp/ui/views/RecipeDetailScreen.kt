package com.db.recipeapp.ui.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.db.recipeapp.components.RecipeDetailDescription
import com.db.recipeapp.components.RecipeDetailImage
import com.db.recipeapp.components.RecipeDetailInfoRow
import com.db.recipeapp.components.RecipeDetailIngredients
import com.db.recipeapp.components.RecipeDetailTitle
import com.db.recipeapp.core.data.local.RecipeUIModel

@Composable
fun RecipeDetailScreen(
    recipe: RecipeUIModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {

        item {
            RecipeDetailTitle(recipe.title)
        }

        item {
            RecipeDetailDescription(recipe.description)
        }

        item {
            RecipeDetailImage(imageUrl = recipe.thumbnailUrl)
        }

        item {
            RecipeDetailInfoRow(recipe.amountLabel, recipe.prepTime, recipe.cookingTime)
        }

        item {
            RecipeDetailIngredients(recipe.ingredients)
        }


    }
}