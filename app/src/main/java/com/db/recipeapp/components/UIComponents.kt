package com.db.recipeapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.db.recipeapp.core.data.local.RecipeUIModel
import com.db.recipeapp.ui.theme.CardBackground
import com.db.recipeapp.ui.theme.ColesRed
import com.db.recipeapp.ui.viewmodels.SnackBarViewModel


private val HzPadding = 16.dp
private val BorderPadding = 8.dp
private val InfoHeight = 96.dp
private val CardImageSize = 120.dp
private val DetailsImageSize = 260.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeTopBar(
    modifier: Modifier = Modifier,
    title: String,
    colors: TopAppBarColors? = null,
    onBackClick: (() -> Unit)?
) {

    TopAppBar(
        modifier = modifier.testTag("TopBar"),
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(start = HzPadding)
                    .semantics { contentDescription = "Top Bar" },
            )
        },
        colors = colors ?: TopAppBarDefaults.topAppBarColors(
            containerColor = ColesRed,
            titleContentColor = Color.White
        ),
        navigationIcon = {
            onBackClick?.let {
                IconButton(onClick = it, modifier = Modifier.size(48.dp)) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Back Button",
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

        }
    )
}


@Composable
fun RecipeItem(
    modifier: Modifier = Modifier,
    recipe: RecipeUIModel,
    onClick: (RecipeUIModel) -> Unit,
    snackBarViewModel: SnackBarViewModel
) {

    Box(
        modifier = modifier
            .height(220.dp)
            .padding(BorderPadding)
            .background(CardBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onClick(recipe)
                },
            horizontalAlignment = Alignment.Start
        ) {
            ImageWithErrorSnackBar(
                imageUrl = recipe.thumbnailUrl,
                contentDescription = recipe.title,
                snackBarViewModel = snackBarViewModel,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CardImageSize)
                    .testTag("RecipeImage")
            )
            Spacer(modifier = Modifier.height(BorderPadding))
            Text(
                text = "Recipe",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = "Recipe label" },
                color = Color.Red,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = recipe.title,
                maxLines = 2,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = recipe.title }
            )
        }
    }
}


@Composable
fun RecipeList(
    recipes: List<RecipeUIModel>,
    onRecipeClick: (RecipeUIModel) -> Unit,
    snackBarViewModel: SnackBarViewModel
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val columns = if (screenWidth < 600.dp) 2 else 3

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        contentPadding = PaddingValues(HzPadding),
        modifier = Modifier.fillMaxSize()
    ) {
        items(recipes, key = { it.title }) { recipe ->
            RecipeItem(
                modifier = Modifier.width(screenWidth),
                recipe = recipe,
                onClick = { onRecipeClick(recipe) },
                snackBarViewModel = snackBarViewModel
            )
        }
    }


}

@Composable
fun RecipeDetailTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            letterSpacing = 2.sp
        ),
        modifier = Modifier
            .padding(vertical = BorderPadding, horizontal = HzPadding)
            .fillMaxSize()
            .semantics { contentDescription = title },
        textAlign = TextAlign.Center
    )
}

@Composable
fun RecipeDetailDescription(description: String) {
    Text(
        text = description,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .padding(HzPadding)
            .semantics { contentDescription = description },
        textAlign = TextAlign.Center
    )
}

@Composable
fun RecipeDetailImage(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(DetailsImageSize)
        .padding(BorderPadding),
    imageUrl: String,
    contentDescription: String,

    ) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier.testTag("RecipeDetailImage"),
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun RecipeDetailInfoRow(
    amountNumber: Int,
    prepTime: String,
    cookingTime: String,
) {

    Row(
        modifier = Modifier
            .height(InfoHeight)
            .fillMaxSize()
            .padding(vertical = HzPadding)
            .borderTopAndBottom(strokeWidth = 1.dp, color = Color.LightGray),
        horizontalArrangement = Arrangement.spacedBy(HzPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        InfoItem(label = "Serves", value = amountNumber.toString(), modifier = Modifier.weight(1f))
        VerticalDivider(
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .padding(vertical = 10.dp),
            thickness = 1.dp
        )
        InfoItem(label = "Prep", value = prepTime, modifier = Modifier.weight(1f))
        VerticalDivider(
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .padding(vertical = 10.dp),
            thickness = 1.dp

        )
        InfoItem(label = "Cooking", value = cookingTime, modifier = Modifier.weight(1f))

    }

}

@Composable
fun InfoItem(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .padding(bottom = 4.dp)
                .semantics { contentDescription = label }
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.ExtraBold),
            modifier = Modifier.semantics { contentDescription = value }

        )
    }
}


@Composable
fun RecipeDetailIngredients(ingredients: List<String>) {
    Text(
        text = "Ingredients",
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            letterSpacing = 2.sp
        ),
        modifier = Modifier
            .padding(horizontal = BorderPadding, vertical = HzPadding)
            .semantics { contentDescription = "Ingredients" }
    )

    Column {
        ingredients.forEach { ingredient ->
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(bottom = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Ingredient Icon",
                    modifier = Modifier.padding(end = BorderPadding)
                )

                Text(
                    text = ingredient,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .semantics { contentDescription = ingredient }
                )
            }
            Spacer(Modifier.size(8.dp))
        }
    }
}

@Composable
fun ImageWithErrorSnackBar(
    imageUrl: String?,
    contentDescription: String,
    snackBarViewModel: SnackBarViewModel,
    modifier: Modifier = Modifier
) {

    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        onError = {
            snackBarViewModel.showErrorMessage("Failed to load image")
        }
    )

}


//
//@Preview(showBackground = true)
//@Composable
//fun RecipeListPreview() {
//    RecipeList(
//        recipes = listOf(
//            RecipeUIModel("Recipe 1", "Description 1", "https://example.com/image1.jpg","8hr","cooking time", emptyList()),
//            RecipeUIModel("Recipe 2", "Description 2", "https://example.com/image2.jpg","8hr","cooking time", emptyList())
//        ),
//        onRecipeClick = {}
//    )
//    RecipeDetailTitle("Test")
//    RecipeDetailInfoRow(amountNumber = 8, prepTime = "15m", cookingTime = "2h45m")
//}