package com.db.recipeapp.viewmodels

import com.db.recipeapp.core.data.local.RecipeUIModel
import com.db.recipeapp.core.domain.RecipeUseCase
import com.db.recipeapp.ui.viewmodels.RecipeUiState
import com.db.recipeapp.ui.viewmodels.RecipeViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RecipeViewModelTest {

    private lateinit var recipeViewModel: RecipeViewModel

    @MockK
    private lateinit var recipeUseCase: RecipeUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        recipeViewModel = RecipeViewModel(recipeUseCase)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }


    @Test
    fun getRecipesSuccess() = runTest {

        val expectedRecipes = listOf(
            RecipeUIModel(
                title = "Apple Pie",
                description = "Delicious apple pie.",
                thumbnailUrl = "apple_pie.jpg",
                amountLabel = 2,
                prepTime = "10 min",
                cookingTime = "40 min",
                ingredients = listOf("Apple", "Sugar", "Flour")
            )
        )

        coEvery { recipeUseCase.getFilteredAndSortedRecipes() } returns flowOf(Result.success(expectedRecipes))


        recipeViewModel.getRecipes()


        val resultState = recipeViewModel.recipesState.first { it is RecipeUiState.Success }

        if (resultState is RecipeUiState.Success) {
            Assert.assertEquals(expectedRecipes, resultState.recipes)
        } else {
            throw AssertionError("Expected Success state, but got $resultState")
        }
    }

    @Test
    fun getRecipesFailed() = runTest  {

        val errorMessage = "Network Error"
        coEvery { recipeUseCase.getFilteredAndSortedRecipes() } returns flowOf(Result.failure(Exception(errorMessage)))

        recipeViewModel.getRecipes()

        val resultState = recipeViewModel.recipesState.first { it is RecipeUiState.Error }

        if (resultState is RecipeUiState.Error) {
            Assert.assertEquals(errorMessage, resultState.errorMessage)
        } else {
            throw AssertionError("Expected Error state, but got $resultState")
        }
    }
}