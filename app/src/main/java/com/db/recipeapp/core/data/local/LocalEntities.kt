package com.db.recipeapp.core.data.local

import kotlinx.serialization.Serializable

@Serializable
data class RecipeUIModel(
    val title: String,
    val description: String,
    val thumbnailUrl: String,
    val amountLabel:Int,
    val prepTime: String,
    val cookingTime: String,
    val ingredients: List<String>
)