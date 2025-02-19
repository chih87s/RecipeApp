package com.db.recipeapp.ui.views

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.db.recipeapp.components.RecipeList
import com.db.recipeapp.core.data.local.RecipeUIModel
import com.db.recipeapp.ui.viewmodels.RecipeUiState
import com.db.recipeapp.ui.viewmodels.RecipeViewModel
import com.db.recipeapp.ui.viewmodels.SnackBarViewModel

@Composable
fun MainRecipeScreen(
    onRecipeTap: (RecipeUIModel) -> Unit,
    snackBarViewModel: SnackBarViewModel,
    viewModel: RecipeViewModel = hiltViewModel()
) {

    val recipeState = viewModel.recipesState.collectAsState()
    val isDataLoaded = viewModel.isDataLoaded.collectAsState()

    LaunchedEffect(isDataLoaded.value) {
        if (!isDataLoaded.value) {
            viewModel.getRecipes()
        }
    }

    when (val state = recipeState.value) {
        is RecipeUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(48.dp).testTag("LoadingIndicator"))
            }
        }

        is RecipeUiState.Success -> {
            RecipeList(
                recipes = state.recipes,
                onRecipeClick = { onRecipeTap(it) },
                showErrorMessage = {
                    snackBarViewModel.showErrorMessage(it)
                }
            )
        }

        is RecipeUiState.Error -> {
            snackBarViewModel.showErrorMessage(state.errorMessage)
        }
    }

}