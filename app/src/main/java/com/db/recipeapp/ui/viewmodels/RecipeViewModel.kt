package com.db.recipeapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.db.recipeapp.core.data.local.RecipeUIModel
import com.db.recipeapp.core.domain.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase
): ViewModel() {

    private val _recipesData = MutableStateFlow<RecipeUiState>((RecipeUiState.Loading))
    val recipesState: StateFlow<RecipeUiState> = _recipesData


    fun getRecipes(){
        viewModelScope.launch {
            recipeUseCase.getFilteredAndSortedRecipes().collect{result ->
                _recipesData.value = when (result.isSuccess) {
                    true -> RecipeUiState.Success(result.getOrNull() ?: emptyList())
                    false -> RecipeUiState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                }
            }
        }
    }
}

sealed class RecipeUiState{
    data object Loading:RecipeUiState()
    data class Success(val recipes:List<RecipeUIModel>):RecipeUiState()
    data class Error(val errorMessage:String):RecipeUiState()
}