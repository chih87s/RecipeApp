package com.db.recipeapp.ui.views

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.db.recipeapp.components.RecipeDetailDescription
import com.db.recipeapp.components.RecipeDetailImage
import com.db.recipeapp.components.RecipeDetailInfoRow
import com.db.recipeapp.components.RecipeDetailIngredients
import com.db.recipeapp.components.RecipeDetailTitle
import com.db.recipeapp.core.data.local.RecipeUIModel
import com.db.recipeapp.ui.viewmodels.SnackBarMessage
import com.db.recipeapp.ui.viewmodels.SnackBarViewModel

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
            RecipeDetailImage(
                imageUrl = recipe.thumbnailUrl,
                contentDescription = recipe.title
            )
        }

        item {
            RecipeDetailInfoRow(recipe.amountLabel, recipe.prepTime, recipe.cookingTime)
        }

        item {
            RecipeDetailIngredients(recipe.ingredients)
        }


    }
}