package com.db.recipeapp.ui.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.db.recipeapp.constants.MainDestinations
import com.db.recipeapp.core.data.local.RecipeUIModel
import com.db.recipeapp.ui.viewmodels.SnackBarViewModel
import com.db.recipeapp.ui.views.MainRecipeScreen
import com.db.recipeapp.ui.views.RecipeDetailScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun NavGraphBuilder.recipeNavGraph(
    navigationManager: NavigationManager,
    snackBarViewModel: SnackBarViewModel
) {
    composable(route = MainDestinations.MainRecipe.route) {
        MainRecipeScreen(
            onRecipeTap = { recipe ->
                navigationManager.navigateToRecipeDetails(recipe)
            },
            snackBarViewModel = snackBarViewModel
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


class NavigationManagerImpl(private val navController: NavController) : NavigationManager {

    override fun navigateToRecipeDetails(recipe: RecipeUIModel) {
        val encodedRecipe = Uri.encode(Json.encodeToString(recipe))
        navController.navigate("${MainDestinations.RecipeDetails.route}/$encodedRecipe")
    }

    override fun popBackStack() {
        navController.popBackStack()
    }
}