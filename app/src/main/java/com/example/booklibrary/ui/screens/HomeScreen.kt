package com.example.booklibrary.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.booklibrary.data.model.Book
import com.example.booklibrary.ui.components.BookCard
import com.example.booklibrary.ui.components.SectionTitle
import com.example.booklibrary.ui.theme.BackgroundDarkCenter
import com.example.booklibrary.ui.theme.BackgroundDarkEnd
import com.example.booklibrary.ui.theme.BackgroundDarkStart
import com.example.booklibrary.ui.theme.BrandPurple
import com.example.booklibrary.ui.theme.CardBackground
import com.example.booklibrary.ui.theme.TextPrimary
import com.example.booklibrary.ui.theme.TextSecondary
import com.example.booklibrary.ui.theme.TrackDark
import com.example.booklibrary.viewmodel.BookViewModel

@Composable
fun HomeScreen(
    viewModel: BookViewModel,
    bottomPadding: Dp = 0.dp
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentlyReadingList by viewModel.currentlyReading.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
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
            item(
                span = { GridItemSpan(2) }
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                GreetingSection()
            }

            item(
                span = { GridItemSpan(2) }
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                if (currentlyReadingList.isNotEmpty()) {
                    // Show the first in-progress book
                    CurrentlyReadingCard(
                        book = currentlyReadingList.first(),
                        onContinueClick = {
                            viewModel.incrementProgress(currentlyReadingList.first().id)
                        }
                    )
                } else {
                    EmptyCurrentlyReadingCard()
                }
            }

            item(
                span = { GridItemSpan(2) }
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                SectionTitle("For You")
            }

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

@Composable
fun GreetingSection(
    name: String = "Apoorva"
) {
    Column {
        Text(
            text = "Good Evening, $name",
            color = TextPrimary,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Pick up where you left off...",
            color = TextSecondary,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun CurrentlyReadingCard(
    book: Book,
    onContinueClick: () -> Unit
) {
    val progress = if (book.totalChapter != null && book.totalChapter > 0) {
        (book.currentChapter ?: 0).toFloat() / book.totalChapter.toFloat()
    } else {
        0f
    }
    val percentage = (progress * 100).toInt()

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Image(
                painter = painterResource(book.imageRes),
                contentDescription = "Book Cover",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(120.dp)
                    .aspectRatio(0.65f)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = book.title,
                    color = TextPrimary,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = book.author,
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "$percentage% completed",
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(15.dp))

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = BrandPurple,
                    trackColor = TrackDark
                )

                Spacer(modifier = Modifier.height(15.dp))

                Button(
                    onClick = onContinueClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BrandPurple
                    ),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        text = "Continue Reading",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyCurrentlyReadingCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "No books in progress",
                    color = TextPrimary,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Go to Explore to start reading a book!",
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
