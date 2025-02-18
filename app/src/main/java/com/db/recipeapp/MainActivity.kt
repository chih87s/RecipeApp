package com.db.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.db.recipeapp.components.RecipeTopBar
import com.db.recipeapp.constants.MainDestinations
import com.db.recipeapp.ui.navigation.recipeNavGraph
import com.db.recipeapp.ui.theme.RecipeAppTheme
import com.db.recipeapp.ui.views.MainRecipeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeApp()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeApp(){
    RecipeAppTheme {
        val mNavController = rememberNavController()
        val scaffoldSnackBarHostState = remember { SnackbarHostState() }

        val currentBackStackEntry by mNavController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route


        Scaffold(
            topBar = {
                RecipeTopBar(
                    title = topBarTitleHandler(currentRoute),
                    onBackClick = if (currentRoute?.startsWith(MainDestinations.RecipeDetails.route) == true) {
                        { mNavController.popBackStack() }
                    } else null
                )            },
            snackbarHost = { SnackbarHost(hostState = scaffoldSnackBarHostState) }
        ) { paddingValues ->

            NavHost(
                navController = mNavController,
                startDestination = MainDestinations.MainRecipe.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                recipeNavGraph(
                    navController = mNavController,
                    scaffoldSnackBarHostState = scaffoldSnackBarHostState
                )
            }
        }

    }
}

@Composable
fun topBarTitleHandler(route: String?): String {
    return when {
        route?.startsWith(MainDestinations.MainRecipe.route) == true -> "Recipe"
        route?.startsWith(MainDestinations.RecipeDetails.route) == true -> "Details"
        else -> "Recipe"
    }
}