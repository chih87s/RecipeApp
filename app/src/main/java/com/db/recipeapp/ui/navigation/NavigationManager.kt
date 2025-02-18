package com.db.recipeapp.ui.navigation

import com.db.recipeapp.core.data.local.RecipeUIModel

interface NavigationManager {
    fun navigateToRecipeDetails(recipe: RecipeUIModel)
    fun popBackStack()
}