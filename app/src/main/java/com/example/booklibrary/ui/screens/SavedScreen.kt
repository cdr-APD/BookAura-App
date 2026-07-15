package com.example.booklibrary.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.booklibrary.ui.components.SavedBookCard
import com.example.booklibrary.ui.components.SectionTitle
import com.example.booklibrary.ui.theme.BackgroundDarkCenter
import com.example.booklibrary.ui.theme.BackgroundDarkEnd
import com.example.booklibrary.ui.theme.BackgroundDarkStart
import com.example.booklibrary.ui.theme.TextPrimary
import com.example.booklibrary.ui.theme.TextSecondary
import com.example.booklibrary.viewmodel.BookViewModel

@Composable
fun SavedScreen(
    viewModel: BookViewModel,
    bottomPadding: Dp = 0.dp
) {
    val savedBooks by viewModel.savedBooks.collectAsStateWithLifecycle()
    val currentlyReading by viewModel.currentlyReading.collectAsStateWithLifecycle()
    val unreadCollection by viewModel.unreadCollection.collectAsStateWithLifecycle()

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
        if (savedBooks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "No saved books yet",
                        color = TextPrimary,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Explore books and bookmark your favorites to build your collection.",
                        color = TextSecondary,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                contentPadding = PaddingValues(
                    top = 20.dp,
                    bottom = bottomPadding + 20.dp
                ),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(40.dp))
                    SectionTitle("Saved Books")
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Your personal collection",
                        color = TextSecondary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // CURRENTLY READING
                if (currentlyReading.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                        SectionTitle("Currently Reading")
                        Spacer(modifier = Modifier.height(16.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(currentlyReading, key = { it.id }) { book ->
                                SavedBookCard(
                                    modifier = Modifier.width(200.dp),
                                    book = book,
                                    onBookmarkClick = {
                                        viewModel.toggleSaved(book.id)
                                    }
                                )
                            }
                        }
                    }
                }

                // UNREAD COLLECTION
                if (unreadCollection.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        SectionTitle("Unread Collection")
                    }
                    items(unreadCollection, key = { it.id }) { book ->
                        SavedBookCard(
                            book = book,
                            onBookmarkClick = {
                                viewModel.toggleSaved(book.id)
                            }
                        )
                    }
                }

                // If saved is not empty, but we have neither in-progress nor unread,
                // it means all saved books are completed! We can display them here.
                val completedSaved = savedBooks.filter {
                    val progress = if (it.totalChapter != null && it.totalChapter > 0) {
                        (it.currentChapter ?: 0).toFloat() / it.totalChapter.toFloat()
                    } else 0f
                    progress >= 1f
                }
                if (completedSaved.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        SectionTitle("Completed Collection")
                    }
                    items(completedSaved, key = { it.id }) { book ->
                        SavedBookCard(
                            book = book,
                            onBookmarkClick = {
                                viewModel.toggleSaved(book.id)
                            }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}