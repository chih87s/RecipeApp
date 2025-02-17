package com.db.recipeapp.ui.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
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

    when(val state = recipeState.value){
        is RecipeUiState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        }
        is RecipeUiState.Success ->{
            RecipeList(state.recipes) { }
        }

        is RecipeUiState.Error ->{
            LaunchedEffect(state.errorMessage) {
                scaffoldSnackBarHostState.showSnackbar(state.errorMessage)
            }
        }
    }

}