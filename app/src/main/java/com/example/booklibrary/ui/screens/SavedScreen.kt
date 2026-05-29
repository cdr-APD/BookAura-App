package com.example.booklibrary.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.booklibrary.R
import com.example.booklibrary.ui.components.SavedBookCard
import com.example.booklibrary.ui.components.SectionTitle

@Composable
fun SavedScreen() {

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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),

            contentPadding = PaddingValues(
                bottom = 80.dp
            ),

            verticalArrangement =
                Arrangement.spacedBy(18.dp)
        ){

            item {

                Spacer(modifier = Modifier.height(60.dp))

                SectionTitle("Saved Books")

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Your personal collection",
                    color = Color(0xFFB0B0B0),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // CURRENTLY READING
            item {

                Spacer(modifier = Modifier.height(12.dp))

                SectionTitle("Currently Reading")

                Spacer(modifier = Modifier.height(16.dp))

                LazyRow(
                    horizontalArrangement =
                        Arrangement.spacedBy(16.dp)
                ) {

                    item {

                        SavedBookCard(
                            modifier = Modifier.width(200.dp),

                            imageRes = R.drawable.primal_hunter_1,
                            title = "Primal Hunter",
                            genre = "Fantasy",
                            rating = 4.8f,
                            currentChapter = 12,
                            totalChapter = 200
                        )
                    }

                    item {

                        SavedBookCard(
                            modifier = Modifier.width(200.dp),

                            imageRes = R.drawable.atomic_habits,
                            title = "Atomic Habits",
                            genre = "Self Help",
                            rating = 4.7f,
                            currentChapter = 35,
                            totalChapter = 80
                        )
                    }
                }
            }

            // UNREAD COLLECTION
            item {

                Spacer(modifier = Modifier.height(10.dp))

                SectionTitle("Unread Collection")
            }

            item {

                SavedBookCard(
                    imageRes = R.drawable.deep_work,
                    title = "Deep Work",
                    genre = "Productivity",
                    rating = 4.4f
                )
            }

            item {

                SavedBookCard(
                    imageRes = R.drawable.rich_dad,
                    title = "Rich Dad Poor Dad",
                    genre = "Finance",
                    rating = 4.5f
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}