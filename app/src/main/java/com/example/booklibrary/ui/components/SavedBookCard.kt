package com.example.booklibrary.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.booklibrary.data.model.Book
import com.example.booklibrary.ui.theme.BrandPurple
import com.example.booklibrary.ui.theme.CardBackground
import com.example.booklibrary.ui.theme.StarRating
import com.example.booklibrary.ui.theme.TextPrimary
import com.example.booklibrary.ui.theme.TextSecondary
import com.example.booklibrary.ui.theme.TrackDark

@Composable
fun SavedBookCard(
    book: Book,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val filledStars = book.rating.toInt()
    val emptyStars = 5 - filledStars
    val stars = "★".repeat(filledStars) + "☆".repeat(emptyStars)

    val progress = if (book.currentChapter != null && book.totalChapter != null && book.totalChapter > 0) {
        book.currentChapter.toFloat() / book.totalChapter.toFloat()
    } else {
        0f
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                Image(
                    painter = painterResource(id = book.imageRes),
                    contentDescription = book.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(20.dp))
                )

                IconButton(
                    onClick = onBookmarkClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(36.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = "Remove Bookmark",
                        tint = BrandPurple,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = book.title,
                color = TextPrimary,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = book.genre,
                color = TextSecondary,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "$stars ${book.rating}",
                color = StarRating,
                style = MaterialTheme.typography.bodyMedium
            )

            if (book.currentChapter != null && book.totalChapter != null) {
                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Ch ${book.currentChapter} / ${book.totalChapter}",
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth(),
                    color = BrandPurple,
                    trackColor = TrackDark
                )
            }
        }
    }
}