package com.example.booklibrary.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun SavedBookCard(
    imageRes: Int,
    title: String,
    genre: String,
    rating: Float,

    currentChapter: Int? = null,
    totalChapter: Int? = null,

    modifier: Modifier = Modifier
) {

    val filledStars = rating.toInt()
    val emptyStars = 5 - filledStars

    val stars =
        "★".repeat(filledStars) +
                "☆".repeat(emptyStars)

    val progress =
        if (
            currentChapter != null &&
            totalChapter != null
        ) {
            currentChapter.toFloat() /
                    totalChapter.toFloat()
        } else {
            0f
        }

    Card(
        modifier = modifier.fillMaxWidth(),

        shape = RoundedCornerShape(28.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E2235)
        )
    ) {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,

                contentScale = ContentScale.Crop,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(20.dp))
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = genre,
                color = Color(0xFFB0B0B0),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "$stars $rating",
                color = Color(0xFFFFC107),
                style = MaterialTheme.typography.bodyMedium
            )

            if (
                currentChapter != null &&
                totalChapter != null
            ) {

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Ch $currentChapter / $totalChapter",
                    color = Color(0xFFB0B0B0),
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                LinearProgressIndicator(
                    progress = { progress },

                    modifier = Modifier.fillMaxWidth(),

                    color = Color(0xFF7C4DFF),
                    trackColor = Color(0xFF3A3F5A)
                )
            }
        }
    }
}