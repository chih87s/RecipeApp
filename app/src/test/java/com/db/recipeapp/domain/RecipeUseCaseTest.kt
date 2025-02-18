package com.db.recipeapp.domain

import com.db.recipeapp.core.data.local.RecipeUIModel
import com.db.recipeapp.core.data.mappers.RecipeMapper
import com.db.recipeapp.core.data.remote.IngredientResponse
import com.db.recipeapp.core.data.remote.Recipe
import com.db.recipeapp.core.data.remote.RecipeDetails
import com.db.recipeapp.core.data.remote.RecipeResponse
import com.db.recipeapp.core.data.repositories.RecipeRepo
import com.db.recipeapp.core.domain.RecipeUseCase
import com.db.recipeapp.core.domain.RecipeUseCaseImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class RecipeUseCaseTest {

    @Mock
    private lateinit var recipeRepo: RecipeRepo

    @Mock
    private lateinit var recipeMapper: RecipeMapper


    private lateinit var recipeUseCase: RecipeUseCase


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        recipeUseCase = RecipeUseCaseImpl(recipeRepo, recipeMapper)
    }

    @Test
    fun `test successful data fetch`() = runBlocking {
        val mockResponse = RecipeResponse(
            listOf(
                Recipe(
                    "Title",
                    "Description",
                    "http://123",
                    RecipeDetails(
                        amountLabel = "Serve",
                        amountNumber = 1,
                        prepLabel = "Prep",
                        prepNote = "prep note",
                        prepTime = "12m",
                        cookingLabel = "Cooking",
                        cookingTime = "3h",
                        cookTimeAsMinutes = 12,
                        prepTimeAsMinutes = 8
                    ),
                    listOf(IngredientResponse("test 1"), IngredientResponse("test 2"))
                )
            )
        )
        val mappedResponse = listOf(
            RecipeUIModel(
                "Title",
                "Description",
                "thumbnailUrl",
                1,
                "10 min",
                "15 min",
                listOf("ingredient")
            )
        )

        `when`(recipeRepo.getRecipes()).thenReturn(flowOf(Result.success(mockResponse)))
        `when`(recipeMapper.mapToUIModel(mockResponse)).thenReturn(mappedResponse)

        val result = recipeUseCase.getFilteredAndSortedRecipes().first()

        Assert.assertTrue(result.isSuccess)
        Assert.assertEquals(mappedResponse, result.getOrNull())
    }

    @Test
    fun `test failed data fetch`() = runBlocking {
        val error = Throwable("Unknown Error")
        `when`(recipeRepo.getRecipes()).thenReturn(flowOf(Result.failure(error)))
        val result = recipeUseCase.getFilteredAndSortedRecipes().first()

        Assert.assertTrue(result.isFailure)
        Assert.assertEquals(error, result.exceptionOrNull())
    }
}