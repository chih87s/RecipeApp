package com.db.recipeapp.ui.navigation

import android.net.Uri
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.db.recipeapp.constants.MainDestinations
import com.db.recipeapp.core.data.local.RecipeUIModel
import com.db.recipeapp.ui.views.MainRecipeScreen
import com.db.recipeapp.ui.views.RecipeDetailScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun NavGraphBuilder.recipeNavGraph(
    navController: NavController,
    scaffoldSnackBarHostState: SnackbarHostState
) {
    composable(route = MainDestinations.MainRecipe.route) {
        MainRecipeScreen(
            onRecipeTap = { recipe ->
                navigateToRecipeDetails(navController, recipe)
            },
            scaffoldSnackBarHostState = scaffoldSnackBarHostState
        )
    }

    composable(
        route = "${MainDestinations.RecipeDetails.route}/{encodedRecipe}",
        arguments = listOf(navArgument("encodedRecipe") { type = NavType.StringType })
    ) { navBackStackEntry ->
        val encodedRecipe = navBackStackEntry.arguments?.getString("encodedRecipe")
        encodedRecipe?.let {
            Json.decodeFromString<RecipeUIModel>(Uri.decode(it))
        }?.let {
            RecipeDetailScreen(recipe = it)
        }
    }
}

fun navigateToRecipeDetails(navController: NavController, recipe: RecipeUIModel) {
    val encodedRecipe = Uri.encode(Json.encodeToString(recipe))
    navController.navigate("${MainDestinations.RecipeDetails.route}/$encodedRecipe")
}