package com.db.recipeapp.repository

import com.db.recipeapp.core.data.remote.IngredientResponse
import com.db.recipeapp.core.data.remote.Recipe
import com.db.recipeapp.core.data.remote.RecipeDetails
import com.db.recipeapp.core.data.remote.RecipeResponse
import com.db.recipeapp.core.data.repositories.RecipeRepo
import com.db.recipeapp.core.data.repositories.RecipeRepoImpl
import com.db.recipeapp.helper.JsonParser
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class RecipeRepoTest {

    @Mock
    private lateinit var jsonParser: JsonParser

    private lateinit var recipeRepo: RecipeRepo


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        recipeRepo = RecipeRepoImpl(jsonParser)
    }

    @Test
    fun `test getRecipes with valid JSON`() = runBlocking {
        val expectedResponse = RecipeResponse(
            recipes = listOf(
                Recipe(
                    dynamicTitle = "Apple Pie",
                    dynamicDescription = "Delicious apple pie.",
                    dynamicThumbnail = "apple_pie.jpg",
                    recipeDetails = RecipeDetails(
                        amountLabel = "2 servings",
                        amountNumber = 2,
                        prepLabel = "Prep Time",
                        prepTime = "10 min",
                        prepNote = "Use fresh apples.",
                        cookingLabel = "Cook Time",
                        cookingTime = "40 min",
                        cookTimeAsMinutes = 40,
                        prepTimeAsMinutes = 10
                    ),
                    ingredients = listOf(IngredientResponse(ingredient = "Apple"))
                )
            )
        )

        `when`(jsonParser.parseJsonFromFile("recipesSample.json", RecipeResponse::class.java))
            .thenReturn(expectedResponse)

        val result = recipeRepo.getRecipes().first()


        Assert.assertTrue(result.isSuccess)
        Assert.assertEquals(expectedResponse, result.getOrNull())

    }

    @Test
    fun `test getRecipes failure`() = runBlocking {
        val exception = RuntimeException("File not found")
        `when`(
            jsonParser.parseJsonFromFile(
                "recipesSample.json",
                RecipeResponse::class.java
            )
        ).thenThrow(exception)

        val resultFlow = recipeRepo.getRecipes().first()

        Assert.assertTrue(resultFlow.isFailure)
        Assert.assertEquals(exception, resultFlow.exceptionOrNull())

    }
}