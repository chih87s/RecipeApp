package com.db.recipeapp.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.db.recipeapp.components.RecipeList
import com.db.recipeapp.core.data.local.RecipeUIModel
import com.db.recipeapp.ui.viewmodels.RecipeUiState
import com.db.recipeapp.ui.viewmodels.RecipeViewModel

@Composable
fun MainRecipeScreen(
    onRecipeTap: (RecipeUIModel) -> Unit,
    scaffoldSnackBarHostState: SnackbarHostState,
    viewModel: RecipeViewModel = hiltViewModel()
) {


    val recipeState = viewModel.recipesState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getRecipes()
    }

    when (val state = recipeState.value) {
        is RecipeUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(48.dp))
            }
        }

        is RecipeUiState.Success -> {
            RecipeList(state.recipes) {
                onRecipeTap(it)
            }
        }

        is RecipeUiState.Error -> {
            LaunchedEffect(state.errorMessage) {
                scaffoldSnackBarHostState.showSnackbar(state.errorMessage)
            }
        }
    }

}