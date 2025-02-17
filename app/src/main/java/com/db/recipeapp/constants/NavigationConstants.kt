package com.db.recipeapp.constants

sealed class MainDestinations(val route: String){
    data object MainRecipe:MainDestinations("recipes")
    data object RecipeDetails:MainDestinations("details")
}