package com.db.recipeapp

import android.content.Context
import android.content.res.AssetManager
import com.db.recipeapp.core.data.remote.IngredientResponse
import com.db.recipeapp.core.data.remote.Recipe
import com.db.recipeapp.core.data.remote.RecipeDetails
import com.db.recipeapp.core.data.remote.RecipeResponse
import com.db.recipeapp.helper.JsonParser
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class JsonParserTest {

    private lateinit var jsonParser: JsonParser

    @Before
    fun setUp(){
        jsonParser = JsonParser()

    }


    @Test
    fun `test parseJsonFromFile with valid JSON from file`() {
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

        val result = jsonParser.parseJsonFromFile("valid.json", RecipeResponse::class.java)

        Assert.assertEquals(expectedResponse, result)
    }

    @Test
    fun `test empty JSON parsing`() {
        val expectedResponse = RecipeResponse(recipes = emptyList())

        val result = jsonParser.parseJsonFromFile("empty.json", RecipeResponse::class.java)

        Assert.assertEquals(expectedResponse, result)
    }

    @Test(expected = RuntimeException::class)
    fun `test invalid JSON parsing`() {
        jsonParser.parseJsonFromFile("invalid_recipes.json", RecipeResponse::class.java)
    }

    @Test(expected = RuntimeException::class)
    fun `test file not found`() {
        jsonParser.parseJsonFromFile("non_existing_file.json", RecipeResponse::class.java)
    }
}