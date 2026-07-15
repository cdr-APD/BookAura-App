package com.example.booklibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.booklibrary.ui.components.BottomNavBar
import com.example.booklibrary.ui.theme.BookLibraryTheme
import androidx.navigation.compose.rememberNavController
import com.example.booklibrary.navigation.NavGraph
import com.example.booklibrary.data.repository.LocalBookRepository
import com.example.booklibrary.viewmodel.BookViewModel
import com.example.booklibrary.viewmodel.BookViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {

    private val repository = LocalBookRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookLibraryTheme {
                val navController = rememberNavController()
                val viewModel: BookViewModel = viewModel(
                    factory = BookViewModelFactory(repository)
                )
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavBar(navController)
                    }
                ) { innerPadding ->
                    NavGraph(
                        navController = navController,
                        viewModel = viewModel,
                        bottomPadding = innerPadding.calculateBottomPadding()
                    )
                }
            }
        }
    }
}