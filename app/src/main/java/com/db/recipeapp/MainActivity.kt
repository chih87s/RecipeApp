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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.db.recipeapp.components.RecipeTopBar
import com.db.recipeapp.constants.MainDestinations
import com.db.recipeapp.ui.navigation.NavigationManager
import com.db.recipeapp.ui.navigation.NavigationManagerImpl
import com.db.recipeapp.ui.navigation.recipeNavGraph
import com.db.recipeapp.ui.theme.RecipeAppTheme
import com.db.recipeapp.ui.viewmodels.SnackBarMessage
import com.db.recipeapp.ui.viewmodels.SnackBarViewModel
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
fun RecipeApp() {

    RecipeAppTheme {
        val mNavController = rememberNavController()
        val snackBarViewModel: SnackBarViewModel = hiltViewModel()
        val navigationManager = remember { NavigationManagerImpl(mNavController) }

        val currentBackStackEntry by mNavController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route
        val snackBarMessage by snackBarViewModel.snackBarMessage.collectAsState()

        val scaffoldSnackBarHostState = remember { SnackbarHostState() }

        LaunchedEffect(snackBarMessage) {
            when (snackBarMessage) {
                is SnackBarMessage.Error -> {
                    val errorMessage = snackBarMessage as SnackBarMessage.Error
                    scaffoldSnackBarHostState.showSnackbar(errorMessage.message)
                    snackBarViewModel.clearSnackBarMessage()
                }

                else -> {
                }
            }
        }


        Scaffold(
            topBar = {
                RecipeTopBar(
                    title = topBarTitleHandler(currentRoute),
                    onBackClick = if (currentRoute?.startsWith(MainDestinations.RecipeDetails.route) == true) {
                        { navigationManager.popBackStack() }
                    } else null
                )
            },
            snackbarHost = { SnackbarHost(hostState = scaffoldSnackBarHostState) }
        ) { paddingValues ->

            RecipeNavHost(
                navController = mNavController,
                snackBarViewModel = snackBarViewModel,
                navigationManager = navigationManager,
                modifier = Modifier.padding(paddingValues)
            )
        }

    }

}

@Composable
fun RecipeNavHost(
    navController: NavHostController,
    snackBarViewModel: SnackBarViewModel,
    navigationManager: NavigationManager,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MainDestinations.MainRecipe.route,
        modifier = modifier
    ) {
        recipeNavGraph(
            snackBarViewModel = snackBarViewModel,
            navigationManager = navigationManager
        )
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