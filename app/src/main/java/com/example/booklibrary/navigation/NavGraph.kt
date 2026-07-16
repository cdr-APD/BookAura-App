package com.example.booklibrary.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.booklibrary.ui.screens.ExploreScreen
import com.example.booklibrary.ui.screens.HomeScreen
import com.example.booklibrary.ui.screens.ProfileScreen
import com.example.booklibrary.ui.screens.SavedScreen
import com.example.booklibrary.ui.screens.BookDetailsScreen
import com.example.booklibrary.viewmodel.BookViewModel
import com.example.booklibrary.viewmodel.ExploreViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

sealed class Screen(val route: String){
    object Home : Screen("home")
    object Explore : Screen("explore")
    object Saved : Screen("saved")
    object Profile : Screen("profile")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: BookViewModel,
    bottomPadding: Dp = 0.dp
){
    val exploreViewModel: ExploreViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(Screen.Home.route){
            HomeScreen(viewModel = viewModel, bottomPadding = bottomPadding)
        }
        composable(Screen.Explore.route){
            ExploreScreen(
                viewModel = exploreViewModel,
                bottomPadding = bottomPadding,
                onBookClick = { bookId ->
                    navController.navigate("book_details/$bookId")
                }
            )
        }
        composable("book_details/{bookId}"){ backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
            BookDetailsScreen(
                bookId = bookId,
                viewModel = exploreViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.Saved.route){
            SavedScreen(viewModel = viewModel, bottomPadding = bottomPadding)
        }
        composable(Screen.Profile.route){
            ProfileScreen(viewModel = viewModel, bottomPadding = bottomPadding)
        }
    }
}
