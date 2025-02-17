package com.db.recipeapp.core.data.local

data class RecipeUIModel(
    val title: String,
    val description: String,
    val thumbnailUrl: String,
    val prepTime: String,
    val cookingTime: String,
    val ingredients: List<String>
)