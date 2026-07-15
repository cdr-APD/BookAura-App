package com.example.booklibrary.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.booklibrary.ui.components.BookCard
import com.example.booklibrary.ui.components.GenreChip
import com.example.booklibrary.ui.components.SearchBar
import com.example.booklibrary.ui.components.SectionTitle
import com.example.booklibrary.ui.state.BookSortOption
import com.example.booklibrary.ui.theme.BackgroundDarkCenter
import com.example.booklibrary.ui.theme.BackgroundDarkEnd
import com.example.booklibrary.ui.theme.BackgroundDarkStart
import com.example.booklibrary.ui.theme.BrandPurple
import com.example.booklibrary.ui.theme.CardBackground
import com.example.booklibrary.ui.theme.TextPrimary
import com.example.booklibrary.ui.theme.TextSecondary
import com.example.booklibrary.viewmodel.BookViewModel

@Composable
fun ExploreScreen(
    viewModel: BookViewModel,
    bottomPadding: Dp = 0.dp
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val genres = listOf(
        "All",
        "Fantasy",
        "Sci-Fi",
        "Finance",
        "Self Help",
        "Mystery",
        "Action"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        BackgroundDarkStart,
                        BackgroundDarkCenter,
                        BackgroundDarkEnd
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
                    bottom = bottomPadding
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(span = { GridItemSpan(2) }) {
                Column {
                    Text(
                        text = "Explore",
                        color = TextPrimary,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Find your next obsession",
                        color = TextSecondary,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    SearchBar(
                        query = uiState.searchQuery,
                        onQueryChange = { viewModel.setSearchQuery(it) }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        genres.forEach { genre ->
                            GenreChip(
                                genre = genre,
                                isSelected = uiState.selectedGenre == genre,
                                onClick = {
                                    viewModel.setSelectedGenre(genre)
                                }
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SectionTitle(
                            title = "Trending",
                            modifier = Modifier.weight(1f)
                        )

                        Box {
                            var expanded by remember { mutableStateOf(false) }
                            TextButton(
                                onClick = { expanded = true },
                                colors = ButtonDefaults.textButtonColors(contentColor = BrandPurple)
                            ) {
                                Text(
                                    text = when (uiState.sortOption) {
                                        BookSortOption.RECENTLY_ADDED -> "Recently Added"
                                        BookSortOption.TITLE_AZ -> "Title A-Z"
                                        BookSortOption.READING_PROGRESS -> "Progress"
                                    },
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Sort Option"
                                )
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.background(CardBackground)
                            ) {
                                BookSortOption.entries.forEach { option ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = when (option) {
                                                    BookSortOption.RECENTLY_ADDED -> "Recently Added"
                                                    BookSortOption.TITLE_AZ -> "Title A-Z"
                                                    BookSortOption.READING_PROGRESS -> "Reading Progress"
                                                },
                                                color = TextPrimary
                                            )
                                        },
                                        onClick = {
                                            viewModel.setSortOption(option)
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            if (uiState.books.isEmpty()) {
                item(span = { GridItemSpan(2) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "No books found",
                                color = TextPrimary,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Try adjusting your search query or genre filter.",
                                color = TextSecondary,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            } else {
                items(uiState.books, key = { it.id }) { book ->
                    BookCard(
                        modifier = Modifier.fillMaxWidth(),
                        book = book,
                        onBookmarkClick = {
                            viewModel.toggleSaved(book.id)
                        }
                    )
                }
            }
        }
    }
}