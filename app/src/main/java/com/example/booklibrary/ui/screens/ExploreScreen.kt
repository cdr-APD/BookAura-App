package com.example.booklibrary.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.booklibrary.R
import com.example.booklibrary.data.model.Book
import com.example.booklibrary.ui.components.BookCard
import com.example.booklibrary.ui.components.GenreChip
import com.example.booklibrary.ui.components.SearchBar
import com.example.booklibrary.ui.components.SectionTitle
import androidx.compose.runtime.*

@Composable
fun ExploreScreen(bottomPadding: Dp = 0.dp) {
    val genres = listOf(
        "Fantasy",
        "Sci-Fi",
        "Finance",
        "Self Help",
        "Mystery",
        "Action"
    )
    var selectedGenre by remember {
        mutableStateOf("Fantasy")
    }

    val books = listOf(
        Book(R.drawable.primal_hunter_1, "Primal Hunter", "Fantasy", 4.8f),
        Book(R.drawable.atomic_habits, "Atomic Habits", "Self Help", 4.7f),
        Book(R.drawable.rich_dad, "Rich Dad Poor Dad", "Finance", 4.5f),
        Book(R.drawable.eternal_thief, "Eternal Thief", "Fantasy", 4.6f),
        Book(R.drawable.deep_work, "Deep Work", "Productivity", 4.4f),
        Book(R.drawable.complete_martial, "Complete Martial Arts Attribute", "Fiction", 4.3f)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F111D),
                        Color(0xFF1A1B2F),
                        Color(0xFF121212)
                    )
                )
            )
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 50.dp,
                    bottom = bottomPadding),

            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item(span = { GridItemSpan(2) }) {

                Column {

                    Text(
                        text = "Explore",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Find your next obsession",
                        color = Color(0xFFB0B0B0),
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    SearchBar()

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        genres.forEach { genre ->

                            GenreChip(
                                genre = genre,

                                isSelected = selectedGenre == genre,

                                onClick = {
                                    selectedGenre = genre
                                }
                            )

                            Spacer(
                                modifier = Modifier.width(10.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    SectionTitle("Trending")
                }
            }

            items(books) { book ->

                BookCard(
                    modifier = Modifier.fillMaxWidth(),
                    imageRes = book.imageRes,
                    title = book.title,
                    genre = book.genre,
                    rating = book.rating
                )
            }
        }
    }
}