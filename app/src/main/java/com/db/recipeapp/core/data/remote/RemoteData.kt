package com.db.recipeapp.core.data.remote

data class RecipeResponse(
    val recipes: List<Recipe>
)

data class  Recipe(
    val dynamicTitle:String,
    val dynamicDescription:String,
    val dynamicThumbnail:String,
    val recipeDetails: RecipeDetails,
    val ingredients: List<IngredientResponse>
)

data class RecipeDetails(
    val amountLabel: String,
    val amountNumber: Int,
    val prepLabel: String,
    val prepTime: String,
    val prepNote: String,
    val cookingLabel: String,
    val cookingTime: String,
    val cookTimeAsMinutes: Int,
    val prepTimeAsMinutes: Int,
)

data class IngredientResponse(
    val ingredient: String
)