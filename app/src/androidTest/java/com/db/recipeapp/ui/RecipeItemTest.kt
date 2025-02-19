package com.db.recipeapp.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.db.recipeapp.components.RecipeItem
import com.db.recipeapp.core.data.local.RecipeUIModel
import com.db.recipeapp.ui.viewmodels.SnackBarMessage
import com.db.recipeapp.ui.viewmodels.SnackBarViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.whenever


class RecipeItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @MockK
    lateinit var snackBarViewModel: SnackBarViewModel

    private val snackBarStateFlow = MutableStateFlow<SnackBarMessage>(SnackBarMessage.Idle)

    private val testRecipe = RecipeUIModel(
        title = "Apple Pie",
        description = "Delicious apple pie.",
        thumbnailUrl = "apple_pie.jpg",
        amountLabel = 2,
        prepTime = "10 min",
        cookingTime = "40 min",
        ingredients = listOf("Apple", "Sugar", "Flour")
    )

    @Before
    fun setup(){
        MockKAnnotations.init(this)
        justRun { snackBarViewModel.showErrorMessage("Failed to load image") }

    }

    @Test
    fun testRecipeItemDisplaysCorrectly() {
        composeTestRule.setContent {
            RecipeItem(
                recipe = testRecipe,
                onClick = {},
                snackBarViewModel = snackBarViewModel
            )
        }

        composeTestRule.onNodeWithText("Apple Pie").assertIsDisplayed()

        composeTestRule.onNodeWithTag("RecipeImage", true).assertExists()

        composeTestRule.onNodeWithText("Apple Pie")
            .performClick()

    }

    @Test
    fun testSnackBarIsDisplayedWhenImageFails() {
        snackBarStateFlow.value = SnackBarMessage.Error("Failed to load image")
        coEvery { snackBarViewModel.snackBarMessage } returns snackBarStateFlow

        composeTestRule.setContent {
            RecipeItem(
                recipe = testRecipe,
                onClick = {},
                snackBarViewModel = snackBarViewModel
            )
        }

        composeTestRule.onNodeWithText("Failed to load image").assertDoesNotExist()
    }

}